package com.maciej.poreba.backend.application.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarNotAvailableForRentException extends RuntimeException {
  private String message;
}
