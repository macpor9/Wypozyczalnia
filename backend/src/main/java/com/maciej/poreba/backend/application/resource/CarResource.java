package com.maciej.poreba.backend.application.resource;

import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.payload.request.AddCarRequest;
import com.maciej.poreba.backend.application.payload.request.SearchCriteria;
import com.maciej.poreba.backend.application.payload.response.CarResponse;
import com.maciej.poreba.backend.application.service.CarService;
import com.maciej.poreba.backend.commons.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cars")
@Slf4j
@RequiredArgsConstructor
public class CarResource {

  private final CarService carService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/car")
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
  @GetMapping("/{registrationNumber}")
  public ResponseEntity<?> findCar(@PathVariable("registrationNumber") @NotEmpty String registrationNumber) {
    log.info("retrieving car {}", registrationNumber);
    return ResponseEntity.ok(carService.getCar(registrationNumber));
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('GOOGLE_USER') or hasRole('FACEBOOK_USER')")
  @GetMapping
  public ResponseEntity<?> getAllCars(){
    log.info("retrieving all cars");
    List<CarResponse> list = carService.getAllCars();
    return ResponseEntity.ok(list);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping("/specificCars")
  public ResponseEntity<?> getSpecificCars(@Valid @RequestBody SearchCriteria searchCriteria,
                                           @RequestParam(required = false) String field,
                                           @RequestParam(required = false) String mode){
    log.info("retrieving all cars with search criteria: {}", searchCriteria);
    List<CarResponse> list = carService.getSpecificCars(searchCriteria, field, mode);
    return ResponseEntity.ok(list);
  }


  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/car/{registrationNumber}")
  public ResponseEntity<?> updateCar(@Valid @RequestBody AddCarRequest addCarRequest,
                                      @PathVariable("registrationNumber") @NotEmpty String registrationNumber){
    log.info("updating car: " + registrationNumber);
    carService.updateCar(addCarRequest, registrationNumber);
    return ResponseEntity.accepted().body(new ApiResponse(true, "Car updated successfully"));

  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/car/{registrationNumber}")
  public ResponseEntity<?> removeCar(@PathVariable("registrationNumber") @NotEmpty String registrationNumber) {
    log.info("removing car: " + registrationNumber);
    carService.removeCar(registrationNumber);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/car/photo/{registrationNumber}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile requestFile, @PathVariable("registrationNumber") @NotEmpty String registrationNumber){
    carService.updatePhoto(requestFile, registrationNumber);

    URI location =
            ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/cars/{registrationNumber}")
                    .buildAndExpand(registrationNumber)
                    .toUri();

    return ResponseEntity.created(location).body(new ApiResponse(true, "File updated successfully"));

  }

  @GetMapping("/car/photo/{registrationNumber}")
  public Object getUserFiles(@PathVariable("registrationNumber") @NotEmpty String registrationNumber, HttpServletResponse response){
    return carService.getPhoto(registrationNumber, response);

  }


}
