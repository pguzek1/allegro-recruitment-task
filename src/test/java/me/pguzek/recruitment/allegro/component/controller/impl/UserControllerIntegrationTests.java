package me.pguzek.recruitment.allegro.component.controller.impl;

import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import me.pguzek.recruitment.allegro.AllegroApplication;
import me.pguzek.recruitment.allegro.utils.TestTools;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * Integration tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {AllegroApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class UserControllerIntegrationTests {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private GraphQLWebClient graphQLWebClient;

    @Test
    public void whenGetStargazerSum_thenCorrectSum() {
        // given
        // when
        Mockito.when(graphQLWebClient.post(Mockito.any(GraphQLRequest.class)))
                .then(x -> Mono.justOrEmpty(TestTools.buildResponse("responses/UserControllerTests/getStargazerSum/response1.json")))
                .then(x -> Mono.justOrEmpty(TestTools.buildResponse("responses/UserControllerTests/getStargazerSum/response2.json")))
                .then(x -> Mono.justOrEmpty(TestTools.buildResponse("responses/UserControllerTests/getStargazerSum/response3.json")));

        // then
        testClient.get()
                .uri("/api/users/test/stargazers")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("totalStargazer").isEqualTo(14472);
    }

    @Test
    public void whenGetLanguages_thenCorrectLanguageList() {
        // given
        // when
        Mockito.when(graphQLWebClient.post(Mockito.any(GraphQLRequest.class)))
                .then(x -> Mono.justOrEmpty(TestTools.buildResponse("responses/UserControllerTests/getLanguages/response1.json")))
                .then(x -> Mono.justOrEmpty(TestTools.buildResponse("responses/UserControllerTests/getLanguages/response2.json")))
                .then(x -> Mono.justOrEmpty(TestTools.buildResponse("responses/UserControllerTests/getLanguages/response3.json")));

        // then
        testClient.get()
                .uri("/api/users/test/languages?direction=ASC&ownerAffiliations=OWNER,COLLABORATOR")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().json(TestTools.readFile("responses/UserControllerTests/getLanguages/response_web.json"));
    }

}
