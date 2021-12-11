package com.maciej.poreba.backend.application.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class SearchCriteria {
    private String brand;
    private String model;
    private int yearOfProductionFrom;
    private int yearOfProductionTo;
    private String registrationNumber;
    private double priceFrom;
    private double priceTo;
    private boolean available;
}
