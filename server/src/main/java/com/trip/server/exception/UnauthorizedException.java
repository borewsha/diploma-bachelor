package com.trip.server.exception;

import org.springframework.http.*;

/**
 * Исключение с кодом ответа 401, от которого наследуются все остальные
 * исключения из этой группы.
 */
public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, message, cause);
    }

}
