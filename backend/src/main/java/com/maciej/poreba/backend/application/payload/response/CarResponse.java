package com.maciej.poreba.backend.application.payload.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.maciej.poreba.backend.application.model.Car;
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
   private String yearOfProduction;
   private double price;
   private String registrationNumber;
   private boolean available;
   private Date availableDate;

   public CarResponse(Car car){
       this.model = car.getModel();
       this.brand = car.getBrand();
       this.yearOfProduction = car.getYearOfProduction();
       this.price = car.getPrice();
       this.registrationNumber = car.getRegistrationNumber();
       this.available = car.isAvailable();
       this.availableDate = car.getReservedUntil();
   }
}
