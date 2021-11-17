package com.maciej.poreba.backend.application.service;

import com.maciej.poreba.backend.application.exception.CarExistsException;
import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.payload.AddCarRequest;
import com.maciej.poreba.backend.application.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car addCar(AddCarRequest addCarRequest) {
        if (Boolean.TRUE.equals(carRepository.existsByRegistrationNumber(addCarRequest.getRegistrationNumber()))) {
            log.warn("car {} already exists", addCarRequest.getRegistrationNumber());

            throw new CarExistsException(String.format("car %s already exists", addCarRequest.getRegistrationNumber()));

        }

        Car car = Car.builder()
                .brand(addCarRequest.getBrand())
                .model(addCarRequest.getModel())
                .yearOfProduction(addCarRequest.getYearOfProduction())
                .price(addCarRequest.getPrice())
                .registrationNumber(addCarRequest.getRegistrationNumber())
                .pictureUrl(addCarRequest.getPictureUrl())
                .available(true)
                .build();

        return carRepository.save(car);


    }

    public Optional<Car> findCarByRegistrationNumber(String registrationNumber) {
        return carRepository.findByRegistrationNumber(registrationNumber);

    }
}
