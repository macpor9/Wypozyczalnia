package com.maciej.poreba.backend.application.resource;

import com.maciej.poreba.backend.application.payload.request.RentCarRequest;
import com.maciej.poreba.backend.application.service.RentService;
import com.maciej.poreba.backend.authservice.model.InstaUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rent")
@Slf4j
@RequiredArgsConstructor
public class RentResource {

    public final RentService rentService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping("/{registrationNumber}")
    public ResponseEntity<?> rentCar(@AuthenticationPrincipal InstaUserDetails instaUserDetails,
            @PathVariable("registrationNumber") String registrationNumber,
            @RequestBody @Valid RentCarRequest rentCarRequest) {
        log.info("renting car {}", registrationNumber);
        return ResponseEntity.ok(
                rentService.rentCarByRegistrationNumber(
                        instaUserDetails.getEmail(),
                        registrationNumber,
                        rentCarRequest.getReservedFrom(),
                        rentCarRequest.getReservedUntil()));
    }
}
