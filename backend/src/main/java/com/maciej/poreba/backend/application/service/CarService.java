package com.maciej.poreba.backend.application.service;

import com.maciej.poreba.backend.application.exception.CarExistsException;
import com.maciej.poreba.backend.application.mappers.CarMapper;
import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.model.Rent;
import com.maciej.poreba.backend.application.payload.request.AddCarRequest;
import com.maciej.poreba.backend.application.payload.request.SearchCriteria;
import com.maciej.poreba.backend.application.payload.response.CarResponse;
import com.maciej.poreba.backend.application.repository.CarRepository;
import com.maciej.poreba.backend.application.repository.RentRepository;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {
  private static final String FILES_PATH = "backend/src/main/resources/photo/";
  private final CarRepository carRepository;
  private final RentRepository rentRepository;
  private final String[] sortFields = new String[] {"brand", "model", "yearOfProduction", "price"};

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
    String path =
            FILES_PATH + registrationNumber + ".jpg";

    try {
      File myFile = new File(path);
      removePhoto(registrationNumber);
      if (!myFile.exists()) myFile.createNewFile();
      FileOutputStream fileOutputStream = new FileOutputStream(myFile, false);
      fileOutputStream.write(requestFile.getBytes());
      fileOutputStream.close();
      Car car =
          carRepository
              .findByRegistrationNumber(registrationNumber)
              .orElseThrow(() -> new ResourceNotFoundException(registrationNumber));
      car.setPictureUrl(path);
      carRepository.deleteByRegistrationNumber(registrationNumber);
      carRepository.save(car);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public List<CarResponse> getAllCars() {
    return carRepository
            .findAll()
            .stream()
            .map(CarMapper.INSTANCE::carToCarResponse)
            .collect(Collectors.toList());
  }

  public CarResponse getCar(String registrationNumber) {
    return carRepository
        .findByRegistrationNumber(registrationNumber)
        .map(CarMapper.INSTANCE::carToCarResponse)
        .orElseThrow(() -> new ResourceNotFoundException(registrationNumber));
  }

  public Object getPhoto(String registrationNumber, HttpServletResponse response) {
    Car car =
        carRepository
            .findByRegistrationNumber(registrationNumber)
            .orElseThrow(
                () -> new ResourceNotFoundException("car with" + registrationNumber + "not found"));

    try {
      String path = car.getPictureUrl();
      File file = new File(path);
      FileInputStream fileInputStream = new FileInputStream(file);
      response.setContentType(MediaType.IMAGE_PNG_VALUE);
      IOUtils.copy(fileInputStream, response.getOutputStream());
      fileInputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("File not found!");
    }
    return null;
  }

  public void removeCar(String registrationNumber) {
    carRepository.delete(
        carRepository
            .findByRegistrationNumber(registrationNumber)
            .orElseThrow(() -> new ResourceNotFoundException(registrationNumber)));

    removePhoto(registrationNumber);

    rentRepository.deleteByRegistrationNumber(registrationNumber);
  }

  public void updateCar(AddCarRequest addCarRequest, String registrationNumber) {
    if (Boolean.FALSE.equals(carRepository.existsByRegistrationNumber(registrationNumber))) {
      log.warn(String.format("car %s does not exist exists", registrationNumber));

      throw new CarExistsException(String.format("car %s does not exist", registrationNumber));
    }

    Car car =
        carRepository
            .findByRegistrationNumber(registrationNumber)
            .orElseThrow(() -> new ResourceNotFoundException(registrationNumber));

    updateCarData(addCarRequest, car);

    List<Rent> rents = rentRepository
            .findAll()
            .stream()
            .filter(e -> e.getRegistrationNumber().equals(registrationNumber))
            .toList();

    carRepository.deleteByRegistrationNumber(registrationNumber);
    carRepository.save(car);

    rentRepository.deleteByRegistrationNumber(registrationNumber);
    rents.forEach(e -> {
      e.setRegistrationNumber(MyUtil.returnOrDefault(e.getRegistrationNumber(), addCarRequest.getRegistrationNumber()));
      rentRepository.save(e);
    });
  }

  private void renameCarPhoto(Car car, String newName){
    String oldPath = FILES_PATH + car.getRegistrationNumber() + ".jpg";

    String newPath = FILES_PATH + newName + ".jpg";
    File oldFile = new File(oldPath);
    File newFile = new File(newPath);
    car.setPictureUrl(MyUtil.returnOrDefault(car.getPictureUrl(), newPath));
    log.info(oldFile.renameTo(newFile)?"renamed file" + car.getRegistrationNumber():"file for car not found" + newName);
  }


  public void removePhoto(String registrationNumber) {
    String path = FILES_PATH + registrationNumber + ".jpg";
    try {
      RandomAccessFile raf = new RandomAccessFile(new File(path), "rw");
      raf.close();
      Files.delete(Path.of(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void updateCarData(AddCarRequest addCarRequest, Car car) {
    renameCarPhoto(car,MyUtil.returnOrDefault(car.getRegistrationNumber(), addCarRequest.getRegistrationNumber()));
//    car.setPictureUrl(MyUtil.returnOrDefault(car.getRegistrationNumber(), addCarRequest.getRegistrationNumber()));
    car.setBrand(MyUtil.returnOrDefault(car.getBrand(), addCarRequest.getBrand()));
    car.setModel(MyUtil.returnOrDefault(car.getModel(), addCarRequest.getModel()));
    car.setYearOfProduction(
        MyUtil.returnOrDefault(car.getYearOfProduction(), addCarRequest.getYearOfProduction()));
    car.setRegistrationNumber(
        MyUtil.returnOrDefault(car.getRegistrationNumber(), addCarRequest.getRegistrationNumber()));
    car.setPrice(MyUtil.returnOrDefault(car.getPrice(), addCarRequest.getPrice()));
  }

  public List<CarResponse> getSpecificCars(
      SearchCriteria searchCriteria, String field, String mode) {
    List<CarResponse> carList = getSortedList(field, mode);
    return getFilteredList(carList, searchCriteria);
  }

  public List<CarResponse> getSortedList(String field, String mode) {
    List<CarResponse> carList;
    if (Arrays.asList(sortFields).contains(field)) {
      carList = carRepository
              .findAll(Sort.by(field))
              .stream()
              .map(CarMapper.INSTANCE::carToCarResponse)
              .toList();

      if (mode != null && mode.equalsIgnoreCase("DESCENDING")) {
        carList = new ArrayList<>(carList);
        Collections.reverse(carList);
      }
    } else carList = carRepository
            .findAll()
            .stream()
            .map(CarMapper.INSTANCE::carToCarResponse)
            .toList();
    return carList;
  }

  public List<CarResponse> getFilteredList(List<CarResponse> list, SearchCriteria searchCriteria) {
    return list.stream()
        .filter(e -> myFilter(e.getBrand(), searchCriteria.getBrand()))
        .filter(e -> myFilter(e.getModel(), searchCriteria.getModel()))
        .filter(e -> e.getYearOfProduction() + 1 > searchCriteria.getYearOfProductionFrom())
        .filter(e -> e.getYearOfProduction() - 1 < searchCriteria.getYearOfProductionTo())
        .filter(e -> myFilter(e.getRegistrationNumber(), searchCriteria.getRegistrationNumber()))
        .filter(e -> e.getPrice() + 1 > searchCriteria.getPriceFrom())
        .filter(e -> e.getPrice() - 1 < searchCriteria.getPriceTo())
        .filter(e -> isCarAvailableFilter(e, searchCriteria))
        .toList();
  }

  private <T> boolean myFilter(T car, T searchCriteria) {
    return car.equals(MyUtil.returnOrDefault(car, searchCriteria));
  }

  private boolean isCarAvailableFilter(CarResponse car, SearchCriteria searchCriteria) {
    return !searchCriteria.isAvailable() || car.isAvailable() == searchCriteria.isAvailable();
  }


}
