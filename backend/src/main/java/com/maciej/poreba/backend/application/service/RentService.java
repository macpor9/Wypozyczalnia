package com.maciej.poreba.backend.application.service;

import com.maciej.poreba.backend.application.exception.CarNotAvailableForRentException;
import com.maciej.poreba.backend.application.mappers.RentMapper;
import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.model.Rent;
import com.maciej.poreba.backend.application.payload.response.RentResponse;
import com.maciej.poreba.backend.application.repository.CarRepository;
import com.maciej.poreba.backend.application.repository.RentRepository;
import com.maciej.poreba.backend.commons.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RentService {

    private final CarRepository carRepository;
    private final RentRepository rentRepository;

    public Rent rentCarByRegistrationNumber(String email, String registrationNumber, Date from, Date to) {
        Car car =
                carRepository
                        .findByRegistrationNumber(registrationNumber)
                        .orElseThrow(() -> new ResourceNotFoundException(registrationNumber));

        if (!car.isAvailable()) throw new CarNotAvailableForRentException(registrationNumber);

        car.setReservedBy(email);
        car.setReservedFrom(from);
        car.setReservedUntil(to);
        car.setAvailable(false);

        Rent rent = Rent.builder()
                .registrationNumber(registrationNumber)
                .email(email)
                .reservedFrom(from)
                .reservedUntil(to)
                .price(calculatePrice(from,to, car))
                .build();


        carRepository.save(car);
        return rentRepository.save(rent);
    }

    private double calculatePrice(Date from, Date to, Car car) {
        long diff = to.getTime() - from.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        return car.getPrice()*days;
    }

    public List<RentResponse> getUserRentHistory(String email){
        return rentRepository.findByEmail(email)
                .stream()
                .map(RentMapper.INSTANCE::rentToRentResponse)
                .toList();
    }

}
