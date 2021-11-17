package com.maciej.poreba.backend.application.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "car")
public class Car {
    @Id
    private String id;

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
    private Date reservedFrom;
    private Date reservedUntil;

    @NotBlank
    private boolean available;
}
