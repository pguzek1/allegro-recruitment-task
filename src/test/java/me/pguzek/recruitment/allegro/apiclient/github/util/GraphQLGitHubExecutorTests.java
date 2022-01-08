package me.pguzek.recruitment.allegro.apiclient.github.util;

import graphql.kickstart.spring.webclient.boot.GraphQLError;
import graphql.kickstart.spring.webclient.boot.GraphQLErrorsException;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.AbstractGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.StargazersGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.partial.GitHubOwnerAffiliation;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.PageInfo;
import me.pguzek.recruitment.allegro.apiclient.github.exception.UserNotFoundAppException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.function.Function;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class GraphQLGitHubExecutorTests {

    @Autowired
    private GraphQLGitHubExecutor executor;

    @Test
    public void whenNoNextPage_thenReturnEmpty() {
        // given
        final var pageInfo = new PageInfo("test2", false, false, "test1");
        final var request = new StargazersGitHubRequestDto();
        request.setLogin("test");
        request.setOwnerAffiliations(List.of(GitHubOwnerAffiliation.OWNER));
        final var function = new Function<AbstractGitHubRequestDto, Mono<String>>() {
            @Override
            public Mono<String> apply(AbstractGitHubRequestDto o) {
                return Mono.just("Hi");
            }
        };

        // when
        // then
        StepVerifier.create(executor.expandQueryToAllPages(pageInfo, request, function))
                .verifyComplete();

    }

    @Test
    public void whenNextPage_thenCallDelegate() {
        // given
        final var pageInfo = new PageInfo("test2", true, false, "test1");
        final var request = new StargazersGitHubRequestDto();
        request.setLogin("test");
        request.setOwnerAffiliations(List.of(GitHubOwnerAffiliation.OWNER));
        final var function = new Function<AbstractGitHubRequestDto, Mono<String>>() {
            @Override
            public Mono<String> apply(AbstractGitHubRequestDto o) {
                return Mono.just("Hi");
            }
        };

        // when
        // then
        StepVerifier.create(executor.expandQueryToAllPages(pageInfo, request, function))
                .expectNextMatches("Hi"::equals)
                .verifyComplete();

    }

    @Test
    public void whenIncorrectErrorType_thenExpectException() {
        // given
        final var throwable = new RuntimeException("whenIncorrectErrorType_thenExpectException");
        final var request = new StargazersGitHubRequestDto();
        request.setLogin("test");
        request.setOwnerAffiliations(List.of(GitHubOwnerAffiliation.OWNER));
        final var function = new Function<AbstractGitHubRequestDto, Mono<String>>() {
            @Override
            public Mono<String> apply(AbstractGitHubRequestDto o) {
                return Mono.just("Hi");
            }
        };

        // when
        // then
        StepVerifier.create(executor.userMightBeOrganizationFallback(throwable, request, function))
                .expectErrorMessage("whenIncorrectErrorType_thenExpectException")
                .verify();

    }

    @Test
    public void whenCorrectErrorType_thenExpectDelegateCall() {
        // given
        final var error1 = new GraphQLError();
        error1.setPath(List.of("user"));
        final var throwable = new GraphQLErrorsException(List.of(error1));

        final var request = new StargazersGitHubRequestDto();
        request.setLogin("test");
        request.setOwnerAffiliations(List.of(GitHubOwnerAffiliation.OWNER));

        final var function = new Function<AbstractGitHubRequestDto, Mono<String>>() {
            @Override
            public Mono<String> apply(AbstractGitHubRequestDto o) {
                return Mono.just("Hi");
            }
        };

        // when
        // then
        StepVerifier.create(executor.userMightBeOrganizationFallback(throwable, request, function))
                .expectNextMatches("Hi"::equals)
                .verifyComplete();
    }

    @Test
    public void whenUserDoesNotExists_thenExpectUserNotFoundException() {
        // given
        final var error1 = new GraphQLError();
        error1.setPath(List.of("user"));
        final var throwableUser = new GraphQLErrorsException(List.of(error1));

        final var error2 = new GraphQLError();
        error2.setPath(List.of("organization"));
        final var throwableOrganization = new GraphQLErrorsException(List.of(error2));

        final var request = new StargazersGitHubRequestDto();
        request.setLogin("test");
        request.setOwnerAffiliations(List.of(GitHubOwnerAffiliation.OWNER));

        final var function = new Function<AbstractGitHubRequestDto, Mono<String>>() {
            @Override
            public Mono<String> apply(AbstractGitHubRequestDto o) {
                return Mono.error(throwableOrganization);
            }
        };

        // when
        // then
        StepVerifier.create(executor.userMightBeOrganizationFallback(throwableUser, request, function))
                .expectError(UserNotFoundAppException.class)
                .verify();
    }


}
