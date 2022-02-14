package com.maciej.poreba.backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarResponse {
   private String model;
   private String brand;
   private int yearOfProduction;
   private double price;
   private String registrationNumber;
   private boolean available;
   private Date availableDate;
}
