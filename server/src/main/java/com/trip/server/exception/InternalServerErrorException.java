package com.trip.server.exception;

import org.springframework.http.*;

/**
 * Исключение с кодом ответа сервера 500, от которого наследуются все остальные
 * исключения из этой группы.
 */
public class InternalServerErrorException extends ApiException {

    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }

}
