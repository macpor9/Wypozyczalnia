package com.maciej.poreba.backend.authservice.service;

import com.maciej.poreba.backend.authservice.client.FacebookClient;
import com.maciej.poreba.backend.authservice.exception.InternalServerException;
import com.maciej.poreba.backend.authservice.model.InstaUserDetails;
import com.maciej.poreba.backend.authservice.model.Profile;
import com.maciej.poreba.backend.authservice.model.Role;
import com.maciej.poreba.backend.authservice.model.User;
import com.maciej.poreba.backend.authservice.model.facebook.FacebookUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacebookService {

    private final FacebookClient facebookClient;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final Random random;


    public String loginUser(String fbAccessToken) {
        var facebookUser = facebookClient.getUser(fbAccessToken);

        return userService.findById(facebookUser.getId())
                .or(() -> Optional.ofNullable(userService.registerUser(convertTo(facebookUser), Role.FACEBOOK_USER)))
                .map(InstaUserDetails::new)
                .map(userDetails -> new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()))
                .map(tokenProvider::generateToken)
                .orElseThrow(() ->
                        new InternalServerException("unable to login facebook user id " + facebookUser.getId()));
    }

    private User convertTo(FacebookUser facebookUser) {
        return User.builder()
                .id(facebookUser.getId())
                .email(facebookUser.getEmail())
                .username(generateUsername(facebookUser.getFirstName(), facebookUser.getLastName()))
                .password(generatePassword(8))
                .userProfile(Profile.builder()
                        .displayName(String
                                .format("%s %s", facebookUser.getFirstName(), facebookUser.getLastName()))
                        .profilePictureUrl(facebookUser.getPicture().getData().getUrl())
                        .build())
                .build();
    }

    private String generateUsername(String firstName, String lastName) {
        int number = random.nextInt(999999);

        return String.format("%s.%s.%06d", firstName, lastName, number);
    }

    private String generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(password);
    }
}
