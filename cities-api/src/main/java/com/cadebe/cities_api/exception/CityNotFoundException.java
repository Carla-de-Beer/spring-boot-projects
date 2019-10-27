package com.cadebe.cities_api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
