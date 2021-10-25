package com.maciej.poreba.backend.authservice.model.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmailAddresses {
    @JsonProperty("value")
    private String email;
    private Metadata metadata;
}
