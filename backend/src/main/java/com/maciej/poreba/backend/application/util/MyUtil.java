package com.maciej.poreba.backend.application.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.util.Objects.isNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyUtil {
    public static <T> T returnOrDefault(T defaultField, T newField){
        return isNull(newField)?defaultField:newField;
    }
}


