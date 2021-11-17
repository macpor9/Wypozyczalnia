package com.maciej.poreba.backend.authservice.exception;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailAlreadyExistsException extends RuntimeException {
    public final String message;
}
