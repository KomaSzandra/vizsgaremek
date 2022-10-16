package hu.progmasters.conference.exceptionhandler.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MinDateValidator implements ConstraintValidator<MinDate, LocalDate> {

    @Value("${minDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate minDate;

    @Override
    public void initialize(MinDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        return minDate.isBefore(value);
    }
}
