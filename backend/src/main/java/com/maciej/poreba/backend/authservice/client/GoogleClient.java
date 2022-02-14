package com.maciej.poreba.backend.authservice.client;

import com.maciej.poreba.backend.authservice.model.google.GoogleUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Log4j2
public class GoogleClient {
  private static final String GOOGLE_PEOPLE_API_BASE = "https://people.googleapis.com";
  private final WebClient webClient;

  public GoogleUser getUser(String accessToken) {
    String fields = "names,emailAddresses,photos";
    String uri =
        GOOGLE_PEOPLE_API_BASE
            + UriComponentsBuilder.fromPath("/v1/people/me")
                .queryParam("personFields", fields)
                .build()
                .toUriString();

    return webClient
        .get()
        .uri(uri)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .retrieve()
        .bodyToMono(GoogleUser.class)
        .block();
  }
}


