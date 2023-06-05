package com.trip.server.validator;

import java.util.*;

import javax.validation.*;

public class BoundingBoxValidator implements ConstraintValidator<BoundingBox, List<Double>> {

    @Override
    public void initialize(BoundingBox constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Double> valueField, ConstraintValidatorContext cxt) {
        return valueField.size() == 4 &&
                Math.abs(valueField.get(0)) <= 90 &&
                Math.abs(valueField.get(1)) <= 180 &&
                Math.abs(valueField.get(2)) <= 90 &&
                Math.abs(valueField.get(3)) <= 180;
    }

}
