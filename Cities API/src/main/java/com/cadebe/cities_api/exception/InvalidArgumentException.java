package com.cadebe.cities_api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String errorMessage) {
        super(errorMessage);
    }
}
