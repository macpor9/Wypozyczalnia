package com.maciej.poreba.backend.authservice.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

  @NotBlank
  @Size(min = 3, max = 40)
  private String name;

  @NotBlank
  @Size(min = 3, max = 15)
  private String surname;

  @NotBlank
  @Size(max = 40)
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 20)
  private String password;

  @NotBlank
  @Size(min = 6, max = 20)
  private String repeatPassword;
}
