package me.pguzek.recruitment.allegro.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Getter
public class WebClientAuthCustomizer implements WebClientCustomizer {

    private final String authMethod;
    private final String authToken;

    public WebClientAuthCustomizer(@Value("${app.graphql.client.jwt.method}") String authMethod,
                                   @Value("${app.graphql.client.jwt.token}") String authToken) {
        this.authMethod = authMethod;
        this.authToken = authToken;
    }

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.defaultHeader(HttpHeaders.AUTHORIZATION, String.join(" ", getAuthMethod(), getAuthToken()));
    }
}
