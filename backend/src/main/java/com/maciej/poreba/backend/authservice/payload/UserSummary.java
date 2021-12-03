package com.maciej.poreba.backend.authservice.payload;

import com.maciej.poreba.backend.authservice.model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserSummary {

  private String id;
  private String name;
  private String surname;
  private Set<Role> roles;
}
