package com.maciej.poreba.backend.authservice.service;

import com.maciej.poreba.backend.authservice.MyUtil;
import com.maciej.poreba.backend.authservice.client.GoogleClient;
import com.maciej.poreba.backend.authservice.exception.InternalServerException;
import com.maciej.poreba.backend.authservice.model.InstaUserDetails;
import com.maciej.poreba.backend.authservice.model.Profile;
import com.maciej.poreba.backend.authservice.model.Role;
import com.maciej.poreba.backend.authservice.model.User;
import com.maciej.poreba.backend.authservice.model.google.GoogleUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleService {

  private final GoogleClient googleClient;
  private final UserService userService;
  private final JwtTokenProvider tokenProvider;
  private final Random random;

  public String loginUser(String fbAccessToken) {
    GoogleUser googleUser = googleClient.getUser(fbAccessToken);

    return userService
        .findByEmail(googleUser.getEmailAddresses().get(0).getEmail())
        .or(
            () ->
                Optional.ofNullable(
                    userService.registerUser(convertTo(googleUser), Role.GOOGLE_USER)))
        .map(InstaUserDetails::new)
        .map(
            userDetails ->
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()))
        .map(tokenProvider::generateToken)
        .orElseThrow(
            () ->
                new InternalServerException(
                    "unable to login Google user id " + googleUser.getId()));
  }

  private User convertTo(GoogleUser googleUser) {
    return User.builder()
        .id(googleUser.getId())
        .email(googleUser.getEmailAddresses().get(0).getEmail())
        .username(googleUser.getEmailAddresses().get(0).getEmail())
        .surname(
            generateUsername(
                googleUser.getNames().get(0).getFirstName(),
                googleUser.getNames().get(0).getLastName()))
        .password(MyUtil.generatePassword(8))
        .userProfile(
            Profile.builder()
                .displayName(
                    String.format(
                        "%s %s",
                        googleUser.getNames().get(0).getFirstName(),
                        googleUser.getNames().get(0).getLastName()))
                .build())
        .build();
  }

  private String generateUsername(String firstName, String lastName) {
    int number = random.nextInt(999999);

    return String.format("%s.%s.%06d", firstName, lastName, number);
  }
}
