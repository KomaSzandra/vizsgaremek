package hu.progmasters.conference.dto.command;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.exceptionhandler.Rank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantCreateCommand {

    @NotBlank(message = "Must not be blank")
    @Schema(description = "Name of the participant", example = "Dr. Jack Doe")
    private String name;

    @NotNull(message = "Date of birth should not be empty")
    @Past(message = "Date of birth should be in the past")
    @Schema(description = "The participant's date of birth", type = "string", format = "date", example = "1964-04-20")
    private LocalDate dateOfBirth;

    @Rank(anyOf = {AcademicRank.ASSISTANT_LECTURER, AcademicRank.CANDIDATE, AcademicRank.PROFESSOR,
            AcademicRank.ASSISTANT_RESEARCH_FELLOW, AcademicRank.ASSOCIATE_PROFESSOR, AcademicRank.SCIENTIFIC_ADVISOR,
            AcademicRank.PROFESSOR_EMERITUS, AcademicRank.SENIOR_LECTURER, AcademicRank.RESEARCH_FELLOW,
            AcademicRank.RESEARCH_PROFESSOR, AcademicRank.SENIOR_RESEARCH_FELLOW})
    @NotNull(message = "Must not be null")
    @Schema(description = "Rank of the lecturer")
    private AcademicRank academicRank;

    @NotBlank(message = "Must not be blank")
    @Schema(description = "Name of the institution", example = "Wigner Adatközpont")
    private String institution;

    @Email(message = "Must be an e-mail address")
    @NotNull(message = "Must not be null")
    @Schema(description = "E-mail address", example = "Dr.doe@participant.com")
    private String email;

}

