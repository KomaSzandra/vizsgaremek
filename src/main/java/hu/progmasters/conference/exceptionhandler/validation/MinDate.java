package hu.progmasters.conference.exceptionhandler.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = MinDateValidator.class)
public @interface MinDate {

    String message() default "You have to be born after 1900";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
