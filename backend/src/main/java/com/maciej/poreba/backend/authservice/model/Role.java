package com.maciej.poreba.backend.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    public static final Role USER = new Role("USER");
    public static final Role FACEBOOK_USER = new Role("FACEBOOK_USER");

    private String name;
}

