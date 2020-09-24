package dev.cadebe.persons_api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PersonNotFoundException extends RuntimeException {

    private static final long SERIAL_VERSION_UID = 1L;

    public PersonNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}


