package com.trip.server.handler;

import javax.validation.*;

import com.trip.server.dto.*;
import com.trip.server.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorDto> handleApiException(ApiException exception) {
        if (exception.getHttpStatus().is5xxServerError()) {
            log.error("ApiException {}: {}", exception.getHttpStatus(), exception.getMessage());
            return new ResponseEntity<>(new ApiErrorDto("Произошла ошибка на сервере"), exception.getHttpStatus());
        }
        log.info("ApiException {}: {}", exception.getHttpStatus(), exception.getMessage());
        return new ResponseEntity<>(new ApiErrorDto(exception.getMessage()), exception.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<InvalidFieldsDto> handleConstraintViolationException(
            ConstraintViolationException exception
    ) {
        var errors = exception.getConstraintViolations().stream()
                .map(cv -> {
                    var fieldName = cv.getPropertyPath().toString();
                    for (var propertyPath : cv.getPropertyPath()) {
                        fieldName = propertyPath.getName();
                    }
                    return new InvalidFieldDto(fieldName, cv.getMessage());
                })
                .toList();

        return ResponseEntity.badRequest().body(
                new InvalidFieldsDto("Некоторые поля не прошли валидацию", errors)
        );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<InvalidFieldsDto> handleBindException(BindException exception) {
        var errors = exception.getBindingResult().getAllErrors().stream()
                .map(e -> (FieldError) e)
                .map(e -> new InvalidFieldDto(e.getField(), e.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(
                new InvalidFieldsDto("Некоторые поля не прошли валидацию", errors)
        );
    }

}
