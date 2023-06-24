package com.trip.server.validator;

import org.springframework.lang.Nullable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class TripAttractionsValidator implements ConstraintValidator<TripAttractions, List<Long>> {

    private static final String INVALID_SIZE = "Должно быть как минимум 1 место для посещения";

    @Override
    public void initialize(TripAttractions constraintAnnotation) {
    }

    @Override
    public boolean isValid(@Nullable List<Long> valueField, ConstraintValidatorContext cxt) {
        if (valueField == null) {
            return true;
        }

        cxt.disableDefaultConstraintViolation();

        if (valueField.isEmpty()) {
            cxt.buildConstraintViolationWithTemplate(INVALID_SIZE).addConstraintViolation();
            return false;
        }

        return true;
    }

}
