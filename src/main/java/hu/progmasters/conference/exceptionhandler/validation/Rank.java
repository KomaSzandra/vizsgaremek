package hu.progmasters.conference.exceptionhandler.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RankValidator.class)
public @interface Rank {

    Class<? extends Enum<?>> enumClass();
    String message() default "Must be any of{enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
