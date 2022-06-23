package hu.progmasters.conference.exceptionhandler;

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

    String message() default "No boomers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
