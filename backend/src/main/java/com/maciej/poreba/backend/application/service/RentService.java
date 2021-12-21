package com.maciej.poreba.backend.application.service;

import com.maciej.poreba.backend.application.exception.CarNotAvailableForRentException;
import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.repository.CarRepository;
import com.maciej.poreba.backend.application.resource.CarResource;
import com.maciej.poreba.backend.commons.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RentService {

    public final CarRepository carRepository;
    public Car rentCarByRegistrationNumber(String email, String registrationNumber, Date from, Date to) {
        Car car =
                carRepository
                        .findByRegistrationNumber(registrationNumber)
                        .orElseThrow(() -> new ResourceNotFoundException(registrationNumber));

        if (!car.isAvailable()) throw new CarNotAvailableForRentException(registrationNumber);

        car.setReservedBy(email);
        car.setReservedFrom(from);
        car.setReservedUntil(to);
        car.setAvailable(false);

        return carRepository.save(car);
    }
}
