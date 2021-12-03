package com.maciej.poreba.backend.application.service;

import com.maciej.poreba.backend.application.exception.CarExistsException;
import com.maciej.poreba.backend.application.exception.CarNotAvailableForRentException;
import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.payload.request.AddCarRequest;
import com.maciej.poreba.backend.application.payload.response.CarResponse;
import com.maciej.poreba.backend.application.repository.CarRepository;
import com.maciej.poreba.backend.commons.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {
  private final CarRepository carRepository;

  public Car addCar(AddCarRequest addCarRequest) {
    if (Boolean.TRUE.equals(
        carRepository.existsByRegistrationNumber(addCarRequest.getRegistrationNumber()))) {
      log.warn("car {} already exists", addCarRequest.getRegistrationNumber());

      throw new CarExistsException(
          String.format("car %s already exists", addCarRequest.getRegistrationNumber()));
    }

    Car car =
        Car.builder()
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

  public Car findCarByRegistrationNumber(String registrationNumber) {
    return carRepository
        .findByRegistrationNumber(registrationNumber)
        .orElseThrow(() -> new ResourceNotFoundException(registrationNumber));
  }

  public Car rentCarByRegistrationNumber(String registrationNumber, Date from, Date to) {
    Car car =
        carRepository
            .findByRegistrationNumber(registrationNumber)
            .orElseThrow(() -> new ResourceNotFoundException(registrationNumber));

    if (!car.isAvailable()) throw new CarNotAvailableForRentException(registrationNumber);
    
    car.setReservedFrom(from);
    car.setReservedUntil(to);
    car.setAvailable(false);

    return carRepository.save(car);
  }

  public void updatePhoto(MultipartFile requestFile, String registrationNumber) {
    String path = new StringBuilder()
            .append("backend/src/main/resources/photo/")
            .append(registrationNumber)
            .append(".jpg")
            .toString();

    try {
      File myFile = new File(path);
      if(!myFile.exists())
        myFile.createNewFile();
      FileOutputStream fileOutputStream = new FileOutputStream(myFile, false);
      fileOutputStream.write(requestFile.getBytes());
      fileOutputStream.close();
      Car car = carRepository.findByRegistrationNumber(registrationNumber)
              .orElseThrow(() -> new ResourceNotFoundException(registrationNumber));
      car.setPictureUrl(path);
      carRepository.save(car);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<CarResponse> getAllCars() {
    return carRepository
            .findAll()
            .stream()
            .map(CarResponse::new)
            .collect(Collectors.toList());
  }
}
