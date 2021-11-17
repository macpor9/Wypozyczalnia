package com.maciej.poreba.backend.authservice.resource;


import com.maciej.poreba.backend.authservice.exception.BadRequestException;
import com.maciej.poreba.backend.authservice.exception.EmailAlreadyExistsException;
import com.maciej.poreba.backend.authservice.exception.UsernameAlreadyExistsException;
import com.maciej.poreba.backend.authservice.model.Profile;
import com.maciej.poreba.backend.authservice.model.Role;
import com.maciej.poreba.backend.authservice.model.User;
import com.maciej.poreba.backend.authservice.payload.*;
import com.maciej.poreba.backend.authservice.service.FacebookService;
import com.maciej.poreba.backend.authservice.service.GoogleService;
import com.maciej.poreba.backend.authservice.service.UserService;
import com.maciej.poreba.backend.ca.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthResource {

    private final UserService userService;
    private final FacebookService facebookService;
    private final GoogleService googleService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping("/facebook/signin")
    public ResponseEntity<?> facebookAuth(@Valid @RequestBody ExternalServiceWithTokenLoginRequest facebookLoginRequest) {
        log.info("facebook login {}", facebookLoginRequest);
        String token = facebookService.loginUser(facebookLoginRequest.getAccessToken());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping("/google/signin")
    public ResponseEntity<?> googleAuth(@Valid @RequestBody ExternalServiceWithTokenLoginRequest googleLoginRequest) {
        log.info("google login {}", googleLoginRequest);
        String token = googleService.loginUser(googleLoginRequest.getAccessToken());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest payload) {
        log.info("creating user {}", payload.getSurname());

        if(!payload.getPassword().equals(payload.getRepeatPassword()))
            throw new BadRequestException("passwords dont match!");

        User user = User
                .builder()
                .surname(payload.getSurname())
                .email(payload.getEmail())
                .password(payload.getPassword())
                .username(payload.getEmail())
                .userProfile(Profile
                        .builder()
                        .displayName(payload.getName())
                        .build())
                .build();

        try {
            userService.registerUser(user, Role.USER);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            throw new BadRequestException(e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true,"User registered successfully"));
    }
}
