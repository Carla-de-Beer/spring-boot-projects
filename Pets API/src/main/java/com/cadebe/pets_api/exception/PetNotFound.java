package com.cadebe.pets_api.exception;

public class PetNotFound extends RuntimeException {

    private static final long SERIAL_VERSIONS_UID = 1L;

    public PetNotFound(String errorMessage) {
        super(errorMessage);
    }
}
