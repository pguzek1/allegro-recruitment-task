package me.pguzek.recruitment.allegro.apiclient.github.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.spring.webclient.boot.GraphQLErrorsException;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import lombok.RequiredArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.AbstractGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.LanguagesResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.PagedRepositoryListResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.StargazersResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.PageInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class GraphQLGitHubExecutor {

    private static final String QUERY_USER_REPOSITORIES = "apiclient/github/paged_repository_list.graphql";
    private static final String QUERY_STARGAZERS = "apiclient/github/paged_stargazers.graphql";
    private static final String QUERY_REPOSITORY_LANGUAGES = "apiclient/github/paged_repository_languages.graphql";

    private final GraphQLWebClient webClient;

    public <T> Mono<T> userIsOrganizationFallback(Throwable throwable, AbstractGitHubRequestDto requestDto, Function<AbstractGitHubRequestDto, Mono<T>> delegate) {
        if (throwable instanceof GraphQLErrorsException) {
            var graphErrors = ((GraphQLErrorsException) throwable).getErrors();

            // TODO: don't like this one
            if (graphErrors.size() == 1
                    && Objects.nonNull(graphErrors.get(0).getPath())
                    && graphErrors.get(0).getPath().size() == 1
                    && "user".equals(graphErrors.get(0).getPath().get(0))) {
                requestDto.setOrganizationTypeUser(true);
                return delegate.apply(requestDto);
            }
        }
        return Mono.error(throwable);
    }

    public <T> Mono<T> expandToAllPages(PageInfo pageInfo, AbstractGitHubRequestDto requestDto, Function<AbstractGitHubRequestDto, Mono<T>> delegate) {
        if (!pageInfo.isHasNextPage()) {
            return Mono.empty();
        }

        AbstractGitHubRequestDto x;

        try {
            final var objectMapper = new ObjectMapper();
            x = objectMapper.readValue(objectMapper.writeValueAsString(requestDto), requestDto.getClass());
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        x.setAfter(pageInfo.endCursor);
        return delegate.apply(x);
    }

    private <T> Mono<T> sendRequest(String query, AbstractGitHubRequestDto variables, Class<T> returnType) {
        return webClient.post(query, new ObjectMapper().convertValue(variables, new TypeReference<>() {}), returnType);
    }

    public Mono<PagedRepositoryListResponseDto> queryPagedRepositories(AbstractGitHubRequestDto variables) {
        return sendRequest(QUERY_USER_REPOSITORIES, variables, PagedRepositoryListResponseDto.class);
    }

    public Mono<StargazersResponseDto> queryStargazers(AbstractGitHubRequestDto variables) {
        return sendRequest(QUERY_STARGAZERS, variables, StargazersResponseDto.class);
    }

    public Mono<LanguagesResponseDto> queryLanguages(AbstractGitHubRequestDto variables) {
        return sendRequest(QUERY_REPOSITORY_LANGUAGES, variables, LanguagesResponseDto.class);
    }
}
