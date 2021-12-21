package com.maciej.poreba.backend.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "rentHistory")
public class Rent {
    @NotBlank
    @DateTimeFormat
    private Date reservedFrom;

    @NotBlank
    @DateTimeFormat
    private Date reservedUntil;

    @NotBlank
    private double price;

    @NotBlank
    private String registrationNumber;

    private String email;
}
