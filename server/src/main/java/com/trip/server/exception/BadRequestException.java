package com.trip.server.exception;

import org.springframework.http.*;

/**
 * Исключение с кодом состояния ответа 400, от которого наследуются все остальные
 * исключения из этой группы.
 */
public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }

}
