package hu.progmasters.conference.dto.command;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.exceptionhandler.validation.MinDate;
import hu.progmasters.conference.exceptionhandler.validation.Rank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @MinDate
    @Schema(description = "The participant's date of birth", type = "string", format = "date", example = "1964-04-20")
    private LocalDate dateOfBirth;

    @Rank(enumClass = AcademicRank.class)
    @NotNull(message = "Must not be null")
    @Schema(description = "Rank of the lecturer", example = "PROFESSOR")
    private String academicRank;

    @NotBlank(message = "Must not be blank")
    @Schema(description = "The institution which employs the participant", example = "Wigner Adatközpont")
    private String institution;

    @Email(message = "Must be an e-mail address")
    @NotBlank(message = "Must not be blank")
    @Schema(description = "E-mail address", example = "Dr.doe@participant.com")
    private String email;

}

