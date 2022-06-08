package hu.progmasters.conference.exceptionhandler;

import hu.progmasters.conference.domain.AcademicRank;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RankValidator.class)
public @interface Rank {

    AcademicRank[] anyOf();
    String message() default "Must be any of{anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
