package com.trip.server.handler;

import javax.validation.*;

import com.trip.server.dto.error.ApiErrorDto;
import com.trip.server.dto.error.InvalidFieldDto;
import com.trip.server.dto.error.InvalidFieldsDto;
import com.trip.server.exception.*;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String SERVER_ERROR = "Произошла ошибка на сервере";

    private static final String SOME_FIELDS_ARE_INVALID = "Некоторые поля не прошли валидацию";

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorDto> handleApiException(ApiException exception) {
        if (exception.getHttpStatus().is5xxServerError()) {
            log.error("ApiException {}: {}", exception.getHttpStatus(), exception.getMessage());
            return new ResponseEntity<>(new ApiErrorDto(SERVER_ERROR), exception.getHttpStatus());
        }
        log.debug("ApiException {}: {}", exception.getHttpStatus(), exception.getMessage());
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
                    return new InvalidFieldDto(fieldName, StringUtils.capitalize(cv.getMessage()));
                })
                .toList();

        return ResponseEntity.badRequest().body(new InvalidFieldsDto(SOME_FIELDS_ARE_INVALID, errors));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<InvalidFieldsDto> handleBindException(BindException exception) {
        var errors = exception.getBindingResult().getAllErrors().stream()
                .map(e -> (FieldError) e)
                .map(e -> new InvalidFieldDto(e.getField(), StringUtils.capitalize(e.getDefaultMessage())))
                .toList();

        return ResponseEntity.badRequest().body(new InvalidFieldsDto(SOME_FIELDS_ARE_INVALID, errors));
    }

}
