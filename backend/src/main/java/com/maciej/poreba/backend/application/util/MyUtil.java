package com.maciej.poreba.backend.application.util;

import static java.util.Objects.isNull;

public class MyUtil {
    public static <T> T returnOrDefault(T defaultField, T newField){
        return isNull(newField)?defaultField:newField;
    }
}
