package com.trip.server.exception;

import org.springframework.http.*;

public class UnprocessableEntityException extends ApiException {

    public UnprocessableEntityException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message, cause);
    }

}
