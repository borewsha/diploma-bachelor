package com.trip.server.validator;

import org.springframework.lang.Nullable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class TripDatesValidator implements ConstraintValidator<TripDates, List<? extends LocalDate>> {

    private static final String INVALID_SIZE = "Должно быть 2 значения: дата начала и дата окончания путешествия";

    private static final String INVALID_SEQUENCE = "Дата начала и дата окончания должны идти в хронологическом порядке";

    private static final String DATES_AFFECT_THE_PAST = "Дата начала и дата окончания не должны быть в прошлом";

    private static final String SHORT_TIME_INTERVAL = "Между датой начала и датой окончания должен быть хотя бы 1 день";

    @Override
    public void initialize(TripDates constraintAnnotation) {
    }

    @Override
    public boolean isValid(@Nullable List<? extends LocalDate> valueField, ConstraintValidatorContext cxt) {
        if (valueField == null) {
            return true;
        }

        var yesterday = ChronoLocalDate.from(LocalDate.now().minusDays(1));
        cxt.disableDefaultConstraintViolation();

        if (valueField.size() != 2) {
            cxt.buildConstraintViolationWithTemplate(INVALID_SIZE).addConstraintViolation();
            return false;
        }

        var startsAt = valueField.get(0);
        var endsAt = valueField.get(1);

        if (startsAt.isAfter(endsAt)) {
            cxt.buildConstraintViolationWithTemplate(INVALID_SEQUENCE).addConstraintViolation();
            return false;
        }
        if (startsAt.isBefore(yesterday) || endsAt.isBefore(yesterday)) {
            cxt.buildConstraintViolationWithTemplate(DATES_AFFECT_THE_PAST).addConstraintViolation();
            return false;
        }
        if (DAYS.between(startsAt, endsAt) < 1) {
            cxt.buildConstraintViolationWithTemplate(SHORT_TIME_INTERVAL).addConstraintViolation();
            return false;
        }

        return true;
    }

}
