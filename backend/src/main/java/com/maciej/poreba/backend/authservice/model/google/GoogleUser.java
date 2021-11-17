package com.maciej.poreba.backend.authservice.model.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maciej.poreba.backend.authservice.model.google.dto.EmailAddresses;
import com.maciej.poreba.backend.authservice.model.google.dto.Names;
import com.maciej.poreba.backend.authservice.model.google.dto.Photos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUser {
  @JsonProperty("resourceName")
  private String id;

  private String etag;
  private List<Names> names;
  private List<Photos> photos;
  private List<EmailAddresses> emailAddresses;
}
