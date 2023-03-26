package com.trip.server.exception;

import org.springframework.http.*;

/**
 * Исключение с кодом состояния ответа 404, от которого наследуются все остальные
 * исключения из этой группы.
 */
public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }

}
