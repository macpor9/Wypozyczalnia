package com.maciej.poreba.backend.authservice.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class JwtAuthenticationResponse {

  @NotNull
  private final String accessToken;
  private String tokenType = "Bearer";
}
