package me.pguzek.recruitment.allegro.apiclient.github.impl;

import lombok.RequiredArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.IGitHubApiClient;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.LanguagesRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.PagedRepositoryListRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.StargazersRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.PagedRepositoryListResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.RepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.SizeableRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.LanguageNode;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.StargazerNode;
import me.pguzek.recruitment.allegro.apiclient.github.util.GraphQLGitHubExecutor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GraphQLGitHubApiClient implements IGitHubApiClient {

    private final GraphQLGitHubExecutor graphQLGitHubExecutor;

    @Override
    public Mono<PagedRepositoryListResponseDto> queryPagedRepository(PagedRepositoryListRequestDto requestDto) {
        return graphQLGitHubExecutor.queryPagedRepositories(requestDto)
                .onErrorResume(fallback -> graphQLGitHubExecutor.userIsOrganizationFallback(fallback, requestDto, graphQLGitHubExecutor::queryPagedRepositories));
    }

    @Override
    public Flux<StargazerNode> queryStargazers(StargazersRequestDto requestDto) {
        return graphQLGitHubExecutor.queryStargazers(requestDto)
                .onErrorResume(fallback -> graphQLGitHubExecutor.userIsOrganizationFallback(fallback, requestDto, graphQLGitHubExecutor::queryStargazers))
                .expand(x -> graphQLGitHubExecutor.expandToAllPages(x.getRepositories().getPageInfo(), requestDto, graphQLGitHubExecutor::queryStargazers))
                .flatMapIterable(x -> x.getRepositories().getEdges())
                .map(RepositoryEdge::getNode);
    }

    @Override
    public Flux<SizeableRepositoryEdge<LanguageNode>> queryLanguages(LanguagesRequestDto requestDto) {
        return graphQLGitHubExecutor.queryLanguages(requestDto)
                .onErrorResume(fallback -> graphQLGitHubExecutor.userIsOrganizationFallback(fallback, requestDto, graphQLGitHubExecutor::queryLanguages))
                .expand(x -> graphQLGitHubExecutor.expandToAllPages(x.getRepositories().getPageInfo(), requestDto, graphQLGitHubExecutor::queryLanguages))
                .flatMapIterable(x -> x.getRepositories().getEdges())
                .map(RepositoryEdge::getNode)
                // TODO: repository might have more than 100 languages
                .filter(x -> Objects.nonNull(x.getLanguages().getEdges()))
                .flatMapIterable(x -> x.getLanguages().getEdges());
    }
}
