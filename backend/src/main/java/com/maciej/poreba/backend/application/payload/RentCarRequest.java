package com.maciej.poreba.backend.application.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
public class RentCarRequest {

  @NotBlank @DateTimeFormat private Date reservedFrom;

  @NotBlank @DateTimeFormat private Date reservedUntil;
}
