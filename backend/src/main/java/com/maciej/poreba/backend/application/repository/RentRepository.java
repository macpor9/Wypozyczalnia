package com.maciej.poreba.backend.application.repository;

import com.maciej.poreba.backend.application.model.Rent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RentRepository extends MongoRepository<Rent, String> {
    Optional<Rent> findByRegistrationNumber(String registrationNumber);
    List<Rent> findByEmail(String email);
    List<Rent> findByReservedUntilBefore(Date date);
    List<Rent> findAllByRegistrationNumber(String registrationNumber);
    void deleteByRegistrationNumber(String registrationNumbers);

}
