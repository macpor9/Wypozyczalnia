package com.maciej.poreba.backend.authservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

  @Id private String id;

  @NotBlank
  @Size(max = 15)
  private String username;

  @NotBlank
  @Size(max = 15)
  private String name;

  @NotBlank
  @Size(max = 15)
  private String surname;

  @NotBlank
  @Size(max = 40)
  @Email
  private String email;

  @NotBlank
  @Size(max = 100)
  @JsonIgnore
  private String password;

  @CreatedDate private Instant createdAt;
  @LastModifiedDate private Instant updatedAt;
  private boolean active;
  private Profile userProfile;
  private Set<Role> roles;

  public User(User user) {
    this.id = user.id;
    this.username = user.username;
    this.name = user.name;
    this.surname = user.surname;
    this.password = user.password;
    this.email = user.email;
    this.active = user.active;
    this.userProfile = user.userProfile;
    this.roles = user.roles;
  }

  public User(String name, String surname, String password, String email, String username) {
    this.name = name;
    this.username = username;
    this.surname = surname;
    this.password = password;
    this.email = email;
    this.active = true;
    this.roles = Set.of(new Role("USER"));
  }
}
