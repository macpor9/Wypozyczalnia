package com.maciej.poreba.backend.application.service;

import com.maciej.poreba.backend.application.exception.CarExistsException;
import com.maciej.poreba.backend.application.exception.CarNotAvailableForRentException;
import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.payload.request.AddCarRequest;
import com.maciej.poreba.backend.application.payload.request.SearchCriteria;
import com.maciej.poreba.backend.application.payload.response.CarResponse;
import com.maciej.poreba.backend.application.repository.CarRepository;
import com.maciej.poreba.backend.application.util.MyUtil;
import com.maciej.poreba.backend.commons.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {
  private static final String FILES_PATH = "backend/src/main/resources/photo/";
  private final CarRepository carRepository;
  private final String[] sortFields = new String[]{"brand", "model", "yearOfProduction", "price"};

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

  public void updatePhoto(MultipartFile requestFile, String registrationNumber) {
    String path = new StringBuilder()
            .append(FILES_PATH)
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

  public Object getPhoto(String registrationNumber, HttpServletResponse response) {
    Car car = carRepository.findByRegistrationNumber(registrationNumber)
            .orElseThrow(() -> new ResourceNotFoundException("car with" + registrationNumber + "not found"));

    try {
      String path = car.getPictureUrl();
      File file = new File(path);
      FileInputStream fileInputStream = new FileInputStream(file);
      response.setContentType(MediaType.IMAGE_PNG_VALUE);
      IOUtils.copy(fileInputStream,response.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("File not found!");
    }
    return null;


  }

  public void removeCar(String registrationNumber) {
    carRepository
            .delete(carRepository
                    .findByRegistrationNumber(registrationNumber)
                    .orElseThrow(() -> new ResourceNotFoundException(registrationNumber)));
  }

  public void updateCar(AddCarRequest addCarRequest, String registrationNumber) {
    if (Boolean.FALSE.equals(
            carRepository.existsByRegistrationNumber(registrationNumber))) {
      log.warn(String.format("car %s does not exist exists", registrationNumber));

      throw new CarExistsException(
              String.format("car %s does not exist", registrationNumber));
    }

    Car car = carRepository.findByRegistrationNumber(registrationNumber)
            .orElseThrow(() -> new ResourceNotFoundException(registrationNumber));

    updateCarData(addCarRequest, car);

    carRepository.save(car);
  }

  private void updateCarData(AddCarRequest addCarRequest, Car car) {
    car.setBrand(MyUtil.returnOrDefault(car.getBrand(), addCarRequest.getBrand()));
    car.setModel(MyUtil.returnOrDefault(car.getModel(), addCarRequest.getModel()));
    car.setYearOfProduction(MyUtil.returnOrDefault(car.getYearOfProduction(), addCarRequest.getYearOfProduction()));
    car.setRegistrationNumber(MyUtil.returnOrDefault(car.getRegistrationNumber(), addCarRequest.getRegistrationNumber()));
    car.setPrice(MyUtil.returnOrDefault(car.getPrice(), addCarRequest.getPrice()));
  }

  public List<CarResponse> getSpecificCars(SearchCriteria searchCriteria, String field, String mode) {
    List<CarResponse> carList = getSortedList(field,mode);
    return getFilteredList(carList, searchCriteria);
  }

  public List<CarResponse> getSortedList(String field, String mode){
    List<CarResponse> carList;
    if (Arrays.asList(sortFields).contains(field)){
      carList = carRepository
              .findAll(Sort.by(field))
              .stream()
              .map(CarResponse::new)
              .toList();

      if (mode != null && mode.equalsIgnoreCase("DESCENDING")) {
        carList = new ArrayList<>(carList);
        Collections.reverse(carList);
      }
    }else carList = carRepository.findAll().stream().map(CarResponse::new).toList();
    return carList;
  }

  public List<CarResponse> getFilteredList(List<CarResponse> list, SearchCriteria searchCriteria){
    return list.stream()
            .filter(e -> myFilter(e.getBrand(), searchCriteria.getBrand()))
            .filter(e -> myFilter(e.getModel(), searchCriteria.getModel()))
            .filter(e -> e.getYearOfProduction()+1>searchCriteria.getYearOfProductionFrom())
            .filter(e -> e.getYearOfProduction()-1<searchCriteria.getYearOfProductionTo())
            .filter(e -> myFilter(e.getRegistrationNumber(), searchCriteria.getRegistrationNumber()))
            .filter(e -> e.getPrice()+1>searchCriteria.getPriceFrom())
            .filter(e -> e.getPrice()-1<searchCriteria.getPriceTo())
            .filter(e -> isCarAvailableFilter(e,searchCriteria))
            .toList();
  }

  private <T> boolean myFilter(T car, T searchCriteria){
    return car.equals(MyUtil.returnOrDefault(car,searchCriteria));
  }

  private boolean isCarAvailableFilter(CarResponse car, SearchCriteria searchCriteria){
    return !searchCriteria.isAvailable() || car.isAvailable() == searchCriteria.isAvailable();
  }

}
