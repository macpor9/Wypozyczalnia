package com.maciej.poreba.backend.authservice.model.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Names {
    @JsonProperty("givenName")
    private String firstName;
    @JsonProperty("familyName")
    private String lastName;
    private Metadata metadata;

}
