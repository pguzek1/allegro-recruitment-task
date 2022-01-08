package me.pguzek.recruitment.allegro.component.service.impl;

import me.pguzek.recruitment.allegro.apiclient.github.IGitHubApiClient;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.LanguagesGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.StargazersGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.SizeableRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.LanguageNode;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.StargazerNode;
import me.pguzek.recruitment.allegro.component.dto.partial.ByteLanguageNodeResponseComparatorFactory;
import me.pguzek.recruitment.allegro.component.dto.request.LanguagesCriteria;
import me.pguzek.recruitment.allegro.component.dto.request.StargazerSumCriteria;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.LongStream;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class UserServiceTests {

    @MockBean
    private IGitHubApiClient gitHubApiClient;

    @Autowired
    private UserService userService;

    @Test
    public void givenFluxOfStargazerNode_whenGetStargazerSum_thenReduceBySum() {
        // given
        final var flux = Flux.fromStream(LongStream.range(1, 100).boxed().map(StargazerNode::new));

        final var stargazerSum = LongStream.range(1, 100).sum();
        final var stargazerSumCriteria = new StargazerSumCriteria();
        stargazerSumCriteria.setUser("test");

        // when
        Mockito.when(gitHubApiClient.queryStargazers(Mockito.any(StargazersGitHubRequestDto.class)))
                .then(x -> flux);
        final var request = userService.getStargazerSum(stargazerSumCriteria);

        // then
        StepVerifier.create(request)
                .expectNextMatches(x -> stargazerSum == x.getTotalStargazer())
                .verifyComplete();

    }

    @Test
    public void givenFluxOfLanguageNodes_whenGetLanguages_thenSumByPopularity() {
        // given
        final var flux = Flux.just(
                new SizeableRepositoryEdge<>(1, "", new LanguageNode("p1")),
                new SizeableRepositoryEdge<>(10, "", new LanguageNode("p1")),
                new SizeableRepositoryEdge<>(100, "", new LanguageNode("p1")),
                new SizeableRepositoryEdge<>(1000, "", new LanguageNode("p2")),
                new SizeableRepositoryEdge<>(10000, "", new LanguageNode("p2")),
                new SizeableRepositoryEdge<>(100000, "", new LanguageNode("p3"))
        );

        final var languagesCriteria = new LanguagesCriteria();
        languagesCriteria.setUser("test");
        languagesCriteria.setDirection(ByteLanguageNodeResponseComparatorFactory.Direction.DESC);
        languagesCriteria.setOrderBy(ByteLanguageNodeResponseComparatorFactory.OrderBy.BYTES);

        // when
        Mockito.when(gitHubApiClient.queryLanguages(Mockito.any(LanguagesGitHubRequestDto.class)))
                .then(x -> flux);
        final var request = userService.getLanguages(languagesCriteria);

        // then
        StepVerifier.create(request)
                .expectNextMatches(x -> "p3".equals(x.getLanguageName()) && 100000L == x.getTotalBytes())
                .expectNextMatches(x -> "p2".equals(x.getLanguageName()) && 11000L == x.getTotalBytes())
                .expectNextMatches(x -> "p1".equals(x.getLanguageName()) && 111L == x.getTotalBytes())
                .verifyComplete();

    }
}
