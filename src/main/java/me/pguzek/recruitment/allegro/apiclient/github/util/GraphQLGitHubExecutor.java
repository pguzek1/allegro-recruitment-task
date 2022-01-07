package me.pguzek.recruitment.allegro.apiclient.github.util;

import graphql.kickstart.spring.webclient.boot.GraphQLErrorsException;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import lombok.RequiredArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.AbstractGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.LanguagesResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.PagedRepositoryListResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.StargazersResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.PageInfo;
import me.pguzek.recruitment.allegro.apiclient.github.exception.UserNotFoundAppException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class GraphQLGitHubExecutor {

    private static final String QUERY_USER_REPOSITORIES = "apiclient/github/paged_repository_list.graphql";
    private static final String QUERY_STARGAZERS = "apiclient/github/paged_stargazers.graphql";
    private static final String QUERY_REPOSITORY_LANGUAGES = "apiclient/github/paged_repository_languages.graphql";

    private final GraphQLWebClient webClient;

    @NonNull
    public <T> Mono<T> userIsOrganizationFallback(Throwable throwable, AbstractGitHubRequestDto requestDto, Function<AbstractGitHubRequestDto, Mono<T>> delegate) {
        if (UserGraphErrorPredicate.check(throwable, UserGraphErrorPredicate.UserType.USER)) {
            requestDto.setOrganizationTypeUser(true);
            return delegate.apply(requestDto)
                    .onErrorMap(GraphQLErrorsException.class, e -> UserGraphErrorPredicate.check(e, UserGraphErrorPredicate.UserType.ORGANIZATION) ? UserNotFoundAppException.of(e) : e);
        }
        return Mono.error(throwable);
    }

    @NonNull
    public <T> Mono<T> expandToAllPages(@NonNull PageInfo pageInfo, @NonNull AbstractGitHubRequestDto requestDto, @NonNull Function<AbstractGitHubRequestDto, Mono<T>> delegate) {
        if (!pageInfo.isHasNextPage()) {
            return Mono.empty();
        }
        return delegate.apply(requestDto.mutateAfterCursor(pageInfo.endCursor));
    }

    @NonNull
    private <T> Mono<T> sendRequest(@NonNull String query, @NonNull AbstractGitHubRequestDto variables, @NonNull Class<T> returnType) {
        return webClient.post(GraphQLRequest.builder().resource(query).variables(variables).build())
                .flatMap(x -> {
                    x.validateNoErrors();
                    return Mono.justOrEmpty(x.getFirst(returnType));
                });
    }

    @NonNull
    public Mono<PagedRepositoryListResponseDto> queryPagedRepositories(@NonNull AbstractGitHubRequestDto variables) {
        return sendRequest(QUERY_USER_REPOSITORIES, variables, PagedRepositoryListResponseDto.class);
    }

    @NonNull
    public Mono<StargazersResponseDto> queryStargazers(@NonNull AbstractGitHubRequestDto variables) {
        return sendRequest(QUERY_STARGAZERS, variables, StargazersResponseDto.class);
    }

    @NonNull
    public Mono<LanguagesResponseDto> queryLanguages(@NonNull AbstractGitHubRequestDto variables) {
        return sendRequest(QUERY_REPOSITORY_LANGUAGES, variables, LanguagesResponseDto.class);
    }
}
