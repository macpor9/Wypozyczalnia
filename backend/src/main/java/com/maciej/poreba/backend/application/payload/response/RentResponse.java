package com.maciej.poreba.backend.application.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentResponse {
    private Date reservedFrom;
    private Date reservedUntil;
    private double price;
    private String registrationNumber;


}
