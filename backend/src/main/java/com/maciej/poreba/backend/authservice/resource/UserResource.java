package com.maciej.poreba.backend.authservice.resource;

import com.maciej.poreba.backend.authservice.model.InstaUserDetails;
import com.maciej.poreba.backend.authservice.model.User;
import com.maciej.poreba.backend.authservice.payload.UserSummary;
import com.maciej.poreba.backend.authservice.service.UserService;
import com.maciej.poreba.backend.commons.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserResource {

  @Autowired private UserService userService;

  @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findUser(@PathVariable("username") String username) {
    log.info("retrieving user {}", username);

    return userService
        .findByUsername(username)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException(username));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findAll() {
    log.info("retrieving all");

    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public UserSummary getCurrentUser(@AuthenticationPrincipal InstaUserDetails userDetails) {
    return UserSummary.builder()
        .id(userDetails.getId())
        .name(userDetails.getName())
        .surname(userDetails.getSurname())
        .roles(userDetails.getRoles())
        .build();
  }


  @PreAuthorize("hasRole('AN') or hasRole('FACEBOOK_USER') or hasRole('GOOGLE_USER')")
  @GetMapping(value = "/summary/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getUserSummary(@PathVariable("username") String username) {
    log.info("retrieving user {}", username);

    return userService
        .findByUsername(username)
        .map(user -> ResponseEntity.ok(convertTo(user)))
        .orElseThrow(() -> new ResourceNotFoundException(username));
  }

  private UserSummary convertTo(User user) {
    return UserSummary.builder()
        .id(user.getId())
        .surname(user.getSurname())
        .roles(user.getRoles())
        .name(user.getName())
        .build();
  }
}
