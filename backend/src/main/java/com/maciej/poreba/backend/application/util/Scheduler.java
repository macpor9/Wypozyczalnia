package com.maciej.poreba.backend.application.util;

import com.maciej.poreba.backend.application.repository.CarRepository;
import com.maciej.poreba.backend.application.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduler {
  private static final long REFRESH_RATIO_MS = 1000 * 60 * 60;

  private final CarRepository carRepository;
  private final RentRepository rentRepository;

  @Scheduled(fixedRate = REFRESH_RATIO_MS)
  private void refreshCarsAvailability() {
    carRepository
        .findByReservedUntilBefore(new Date())
        .forEach(
            car -> {
              car.setAvailable(true);
              carRepository.save(car);
            });
  }

}
