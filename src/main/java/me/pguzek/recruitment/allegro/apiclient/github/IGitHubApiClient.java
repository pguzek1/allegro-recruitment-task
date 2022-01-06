package me.pguzek.recruitment.allegro.apiclient.github;

import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.LanguagesRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.PagedRepositoryListRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.StargazersRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.PagedRepositoryListResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.SizeableRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.LanguageNode;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.StargazerNode;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface IGitHubApiClient {

    Mono<PagedRepositoryListResponseDto> queryPagedRepository(PagedRepositoryListRequestDto requestDto);

    Flux<StargazerNode> queryStargazers(StargazersRequestDto requestDto);

    Flux<SizeableRepositoryEdge<LanguageNode>> queryLanguages(LanguagesRequestDto requestDto);

}
