package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.exceptionhandler.Rank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LecturerUpdateCommand {

    @NotBlank(message = "Must not be blank!")
    @Rank(anyOf = {AcademicRank.ASSISTANT_LECTURER, AcademicRank.CANDIDATE, AcademicRank.PROFESSOR,
            AcademicRank.ASSISTANT_RESEARCH_FELLOW, AcademicRank.ASSOCIATE_PROFESSOR, AcademicRank.SCIENTIFIC_ADVISOR,
            AcademicRank.PROFESSOR_EMERITUS, AcademicRank.SENIOR_LECTURER, AcademicRank.RESEARCH_FELLOW,
            AcademicRank.RESEARCH_PROFESSOR, AcademicRank.SENIOR_RESEARCH_FELLOW})
    @Schema(description = "Rank of the lecturer", example = "Professor")
    private AcademicRank academicRank;
}
