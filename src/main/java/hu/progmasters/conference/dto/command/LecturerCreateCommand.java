package hu.progmasters.conference.dto.command;

import hu.progmasters.conference.domain.AcademicRank;
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
public class LecturerCreateCommand {

    @NotBlank(message = "Must not be blank")
    @Schema(description = "Name of the Lecturer", example = "Dr. John Doe")
    private String name;

    @Rank(enumClass = AcademicRank.class)
    @NotNull(message = "Must not be null")
    @Schema(description = "Rank of the lecturer", example = "PROFESSOR")
    private String academicRank;

    @NotBlank(message = "Must not be blank")
    @Schema(description = "Name of the Institution", example = "Central European University")
    private String institution;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Must not be blank")
    @Schema(description = "The Lecturer's e-mail address", example = "doeProfessor@lecturer.com")
    private String email;

    @NotNull(message = "Date of birth should not be empty")
    @Past(message = "Date of birth should be in the past")
    @Schema(description = "The lecturer's date of birth", format = "date", example = "1964-04-20")
    private LocalDate dateOfBirth;

}
