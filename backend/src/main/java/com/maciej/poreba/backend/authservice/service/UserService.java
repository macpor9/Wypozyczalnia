package com.maciej.poreba.backend.authservice.service;

import com.maciej.poreba.backend.authservice.exception.EmailAlreadyExistsException;
import com.maciej.poreba.backend.authservice.model.Role;
import com.maciej.poreba.backend.authservice.model.User;
import com.maciej.poreba.backend.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;

  public String loginUser(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));

    return tokenProvider.generateToken(authentication);
  }

  public User registerUser(User user, Role role) {
    log.info("registering user {}", user.getSurname());
    if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()))) {
      log.warn("email {} already exists.", user.getEmail());

      throw new EmailAlreadyExistsException(
          String.format("email %s already exists", user.getEmail()));
    }
    user.setActive(true);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    if(role == Role.USER)
      user.setRoles(Set.of(role));
    else
      user.setRoles(Set.of(role, Role.USER));

    return userRepository.save(user);
  }

  public List<User> findAll() {
    log.info("retrieving all users");
    return userRepository.findAll();
  }

  public Optional<User> findByUsername(String username) {
    log.info("retrieving user {}", username);
    return userRepository.findByUsername(username);
  }

  public Optional<User> findById(String id) {
    log.info("retrieving user {}", id);
    return userRepository.findById(id);
  }

  public Optional<User> findByEmail(String email) {
    log.info("retrieving user {}", email);
    return userRepository.findByEmail(email);
  }
}
