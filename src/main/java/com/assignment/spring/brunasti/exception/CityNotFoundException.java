package com.assignment.spring.brunasti.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Player")
public class CityNotFoundException extends ResponseStatusException {
    public CityNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND,"Could not find City [" + id+ "]");
    }
}
