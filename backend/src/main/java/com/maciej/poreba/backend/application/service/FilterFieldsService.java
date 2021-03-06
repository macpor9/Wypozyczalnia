package com.maciej.poreba.backend.application.service;

import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterFieldsService {
  private final CarRepository carRepository;

  public List<String> getModels(String brand) {
    return carRepository.findAll().stream()
        .filter(e -> brand == null || e.getBrand().equals(brand))
        .map(Car::getModel)
        .distinct()
        .toList();
  }

  public List<String> getBrands() {
    return carRepository.findAll().stream()
            .map(Car::getBrand)
            .distinct()
            .toList();
  }
}
