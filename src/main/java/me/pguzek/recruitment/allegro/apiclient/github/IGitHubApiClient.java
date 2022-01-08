package me.pguzek.recruitment.allegro.apiclient.github;

import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.LanguagesGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.PagedRepositoriesGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.StargazersGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.PagedRepositoryListResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.SizeableRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.LanguageNode;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.StargazerNode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Component
public interface IGitHubApiClient {

    Mono<PagedRepositoryListResponseDto> queryPagedRepositories(@NonNull @Valid PagedRepositoriesGitHubRequestDto requestDto);

    Flux<StargazerNode> queryStargazers(@NonNull @Valid StargazersGitHubRequestDto requestDto);

    Flux<SizeableRepositoryEdge<LanguageNode>> queryLanguages(@NonNull @Valid LanguagesGitHubRequestDto requestDto);

}
