package com.maciej.poreba.backend.authservice.client;

import com.maciej.poreba.backend.authservice.model.google.GoogleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class GoogleClient {
    private final WebClient webClient;

    private static final String FACEBOOK_GRAPH_API_BASE = "https://graph.facebook.com";

    public GoogleUser getUser(String accessToken) {
        String fields = "email,first_name,last_name,id,picture.width(720).height(720)";
        String uri = FACEBOOK_GRAPH_API_BASE + UriComponentsBuilder
                .fromPath("/me")
                .queryParam("fields", fields)
                .queryParam("redirect", "false")
                .queryParam("access_token", accessToken)
                .build()
                .toUriString();

        return webClient.get()
                .uri(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(GoogleUser.class)
                .block();


    }
}
