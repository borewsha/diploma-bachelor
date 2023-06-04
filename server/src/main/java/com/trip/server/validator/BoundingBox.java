package com.trip.server.validator;

import java.lang.annotation.*;

import javax.validation.*;

@Documented
@Constraint(validatedBy = BoundingBoxValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BoundingBox {

    String message() default "Должно быть 4 значения: юг (-90:90), запад (-180:180), север (-90:90), восток (-180:180)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

