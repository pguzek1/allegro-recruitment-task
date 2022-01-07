package me.pguzek.recruitment.allegro.component.service.impl;

import lombok.RequiredArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.IGitHubApiClient;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.StargazerNode;
import me.pguzek.recruitment.allegro.component.dto.partial.ByteLanguageNodeResponseComparatorFactory;
import me.pguzek.recruitment.allegro.component.dto.request.LanguagesCriteria;
import me.pguzek.recruitment.allegro.component.dto.request.PagedRepositoriesCriteria;
import me.pguzek.recruitment.allegro.component.dto.request.StargazerSumCriteria;
import me.pguzek.recruitment.allegro.component.dto.response.ByteLanguageNodeResponseDto;
import me.pguzek.recruitment.allegro.component.dto.response.PagedRepositoryResponseDto;
import me.pguzek.recruitment.allegro.component.dto.response.TotalStargazerResponseDto;
import me.pguzek.recruitment.allegro.component.service.IUserService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Validated
public class UserService implements IUserService {

    private final IGitHubApiClient gitHubApiClient;

    @Override
    public Mono<PagedRepositoryResponseDto> getPagedRepositories(@NonNull @Valid PagedRepositoriesCriteria criteria) {
        return gitHubApiClient.queryPagedRepositories(criteria.toGitHubRequestDto())
                .map(PagedRepositoryResponseDto::from);
    }

    @Override
    public Mono<TotalStargazerResponseDto> getStargazerSum(@NonNull @Valid StargazerSumCriteria criteria) {
        return gitHubApiClient.queryStargazers(criteria.toGitHubRequestDto())
                .map(StargazerNode::getStargazerCount)
                .reduce(0L, Long::sum)
                .map(TotalStargazerResponseDto::from);
    }

    @Override
    public Flux<ByteLanguageNodeResponseDto> getLanguages(@NonNull @Valid LanguagesCriteria criteria) {
        return gitHubApiClient.queryLanguages(criteria.toGitHubRequestDto())
                .map(ByteLanguageNodeResponseDto::from)
                .collect(Collectors.toMap(ByteLanguageNodeResponseDto::getLanguageName, ByteLanguageNodeResponseDto::getTotalBytes, Long::sum))
                .flatMapIterable(Map::entrySet)
                .map(ByteLanguageNodeResponseDto::from)
                .sort(ByteLanguageNodeResponseComparatorFactory.build(criteria.getDirection(), criteria.getOrderBy()));
    }
}
