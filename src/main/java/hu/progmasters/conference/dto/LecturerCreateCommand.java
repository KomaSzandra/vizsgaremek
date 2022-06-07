package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerCreateCommand {

    @NotBlank(message = "Must not be blank")
    @Schema(description = "Name of the Lecturer", example = "Dr. John Doe")
    private String name;

    @NotNull
    @Schema(description = "Rank of the lecturer", enumAsRef = true)
    private AcademicRank academicRank;

    @NotBlank(message = "Must not be blank")
    @Schema(description = "Name of the Institution", example = "Central European University")
    private String institution;

    @Email(message = "Email should be valid")
    @Schema(description = "The Lecturer's e-mail address", example = "ludwig@ceu.com")
    private String email;

    @NotNull(message = "Date of birth should not be empty")
    @Past(message = "Date of birth should be in the past")
    @Schema(description = "The lecturer's date of birth", format = "date", example = "1964-04-20")
    private LocalDate dateOfBirth;
}
