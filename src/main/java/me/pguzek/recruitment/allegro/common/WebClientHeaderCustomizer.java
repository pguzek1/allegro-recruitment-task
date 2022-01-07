package me.pguzek.recruitment.allegro.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Getter
public class WebClientHeaderCustomizer implements WebClientCustomizer {

    private final String authMethod;
    private final String authToken;
    private final String acceptHeader;

    public WebClientHeaderCustomizer(@Value("${app.graphql.client.jwt.method}") String authMethod,
                                     @Value("${app.graphql.client.jwt.token}") String authToken,
                                     @Value("${app.graphql.client.header.accept}") String acceptHeader) {
        this.authMethod = authMethod;
        this.authToken = authToken;
        this.acceptHeader = acceptHeader;
    }

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.defaultHeader(HttpHeaders.ACCEPT, acceptHeader);
        webClientBuilder.defaultHeader(HttpHeaders.AUTHORIZATION, String.join(" ", getAuthMethod(), getAuthToken()));
    }
}
