package com.trip.server.exception;

import lombok.*;
import org.springframework.http.*;

/**
 * Самый верхний уровень иерархии API исключений, от которого
 * наследуются все остальные API исключения.
 */
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

}
