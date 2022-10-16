package hu.progmasters.conference.exceptionhandler.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankValidator implements ConstraintValidator<Rank, CharSequence> {

    private List<String> subset;

    @Override
    public void initialize(Rank constraintAnnotation) {
        subset = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else {
            return subset.contains(value.toString());
        }
    }
}
