package com.maciej.poreba.backend.authservice.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalServiceWithTokenLoginRequest implements Serializable {

  @NotBlank private String accessToken;
}
