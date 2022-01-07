package com.maciej.poreba.backend.application.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class AddCarRequest {
  @NotEmpty
  @Size(max = 15)
  private String brand;

  @NotEmpty
  @Size(max = 15)
  private String model;

  @NotEmpty
  @Size(max = 15)
  private int yearOfProduction;

  @NotEmpty
  @Size(max = 15)
  private double price;

  @NotEmpty
  @Size(max = 15)
  private String registrationNumber;

  private String pictureUrl;
}
