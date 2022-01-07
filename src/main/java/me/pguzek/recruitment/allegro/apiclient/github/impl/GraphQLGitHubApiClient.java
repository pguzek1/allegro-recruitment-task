package me.pguzek.recruitment.allegro.apiclient.github.impl;

import lombok.RequiredArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.IGitHubApiClient;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.LanguagesGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.PagedRepositoriesGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.StargazersGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.PagedRepositoryListResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.RepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.SizeableRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.LanguageNode;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.StargazerNode;
import me.pguzek.recruitment.allegro.apiclient.github.util.GraphQLGitHubExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Validated
public class GraphQLGitHubApiClient implements IGitHubApiClient {

    private final GraphQLGitHubExecutor graphQLGitHubExecutor;

    @NonNull
    @Override
    public Mono<PagedRepositoryListResponseDto> queryPagedRepositories(@NonNull @Valid PagedRepositoriesGitHubRequestDto requestDto) {
        return graphQLGitHubExecutor.queryPagedRepositories(requestDto)
                .onErrorResume(fallback -> graphQLGitHubExecutor.userIsOrganizationFallback(fallback, requestDto, graphQLGitHubExecutor::queryPagedRepositories));
    }

    @NonNull
    @Override
    public Flux<StargazerNode> queryStargazers(@NonNull @Valid StargazersGitHubRequestDto requestDto) {
        return graphQLGitHubExecutor.queryStargazers(requestDto)
                .onErrorResume(fallback -> graphQLGitHubExecutor.userIsOrganizationFallback(fallback, requestDto, graphQLGitHubExecutor::queryStargazers))
                // TODO: sort by stargazers, if last node on a page has zero stargazers then we dont need to get another page
                .expand(x -> graphQLGitHubExecutor.expandToAllPages(x.getRepositories().getPageInfo(), requestDto, graphQLGitHubExecutor::queryStargazers))
                .flatMapIterable(x -> x.getRepositories().getEdges())
                .map(RepositoryEdge::getNode);
    }

    @NonNull
    @Override
    public Flux<SizeableRepositoryEdge<LanguageNode>> queryLanguages(@NonNull @Valid LanguagesGitHubRequestDto requestDto) {
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
