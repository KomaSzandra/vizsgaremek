package hu.progmasters.conference.exceptionhandler;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MinDateValidator implements ConstraintValidator<MinDate, LocalDate> {

    @Value("1900-04-20")
    private LocalDate minValue;

    @Override
    public void initialize(MinDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value.isAfter(minValue);
    }
}
