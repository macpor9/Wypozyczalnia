package com.maciej.poreba.backend.application.repository;

import com.maciej.poreba.backend.application.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CarRepository extends MongoRepository<Car, String> {
  Optional<Car> findByRegistrationNumber(String registrationNumber);

  Boolean existsByRegistrationNumber(String registrationNumber);
}
