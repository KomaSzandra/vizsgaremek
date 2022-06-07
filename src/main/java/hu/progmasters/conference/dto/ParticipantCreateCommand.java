package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ParticipantCreateCommand {

    @NotBlank(message = "Must not be blank")
    @Schema(description = "Name of the participant", example = "Dr. Jack Doe")
    private String name;

    @NotBlank(message = "Must not be blank")
    @Schema(description = "Name of the institution", example = "Wigner Adatközpont")
    private String institution;

    @NotNull
    @Schema(description = "Date of the registration")
    private LocalDateTime registration;

    @Email(message = "Must be an e-mail address")
    @Schema(description = "E-mail address", example = "doedr@participant.com")
    private String email;

    @NotNull
    @Schema(description = "Rank of the lecturer")
    private AcademicRank academicRank;

    @NotNull(message = "Date of birth should not be empty")
    @Past(message = "Date of birth should be in the past")
    @Schema(description = "The participant's date of birth", type = "string", format = "date", example = "1964-04-20")
    private LocalDate dateOfBirth;
}

