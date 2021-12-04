package com.maciej.poreba.backend.application.util;

import java.util.Objects;

public class MyUtil {
    public static <T extends Object> T returnOrDefault(T defaultField, T newField){
        return Objects.isNull(newField)?defaultField:newField;
    }
}
