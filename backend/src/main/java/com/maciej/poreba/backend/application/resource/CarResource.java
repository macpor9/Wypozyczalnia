package com.maciej.poreba.backend.application.resource;

import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.payload.AddCarRequest;
import com.maciej.poreba.backend.application.payload.RentCarRequest;
import com.maciej.poreba.backend.application.service.CarService;
import com.maciej.poreba.backend.commons.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cars/")
@Slf4j
@RequiredArgsConstructor
public class CarResource {

  private final CarService carService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("car")
  public ResponseEntity<?> addCar(@Valid @RequestBody AddCarRequest addCarRequest) {
    log.info("adding car {}", addCarRequest);
    Car car = carService.addCar(addCarRequest);

    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/cars/{registrationNumber}")
            .buildAndExpand(car.getRegistrationNumber())
            .toUri();

    return ResponseEntity.created(location).body(new ApiResponse(true, "Car added successfully"));
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping("{registrationNumber}")
  public ResponseEntity<?> findCar(@PathVariable("registrationNumber") String registrationNumber) {
    log.info("retrieving car {}", registrationNumber);
    return ResponseEntity.ok(carService.findCarByRegistrationNumber(registrationNumber));
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping("rentCar/{registrationNumber}")
  public ResponseEntity<?> rentCar(
      @PathVariable("registrationNumber") String registrationNumber,
      @RequestBody @Valid RentCarRequest rentCarRequest) {
    log.info("renting car {}", registrationNumber);
    return ResponseEntity.ok(
        carService.rentCarByRegistrationNumber(
            registrationNumber,
            rentCarRequest.getReservedFrom(),
            rentCarRequest.getReservedUntil()));
  }
}
