package com.maciej.poreba.backend.application.exception;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarExistsException extends RuntimeException {
    public final String message;
}
