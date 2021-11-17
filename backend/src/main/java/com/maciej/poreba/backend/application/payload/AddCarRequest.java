package com.maciej.poreba.backend.application.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class AddCarRequest {
    @NotBlank
    @Size(max = 15)
    private String brand;

    @NotBlank
    @Size(max = 15)
    private String model;

    @NotBlank
    @Size(max = 15)
    private String yearOfProduction;

    @NotBlank
    @Size(max = 15)
    private String price;

    @NotBlank
    @Size(max = 15)
    private String registrationNumber;

    private String pictureUrl;
}
