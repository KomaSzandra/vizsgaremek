package hu.progmasters.conference.exceptionhandler;

import hu.progmasters.conference.domain.AcademicRank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RankValidator implements ConstraintValidator<Rank, AcademicRank> {

    private AcademicRank[] subset;

    @Override
    public void initialize(Rank constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(AcademicRank academicRank, ConstraintValidatorContext context) {
        return academicRank == null || Arrays.asList(subset).contains(academicRank);
    }
}
