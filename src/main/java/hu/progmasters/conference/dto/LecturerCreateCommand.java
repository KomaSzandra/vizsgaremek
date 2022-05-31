package hu.progmasters.conference.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerCreateCommand {

    @NotBlank(message = "must not be blank")
    @Schema(description = "Name of the Lecturer", example = "Dr. Ludwig Bobek")
    private String name;

    @NotBlank(message = "must not be blank")
    @Schema(description = "Rank of the lecturer", example = "Associate professor")
    private String academicRank;

    @NotBlank(message = "must not be blank")
    @Schema(description = "Name of the Institution", example = "Central European University")
    private String institution;

    @Email(message = "Must be an e-mail address")
    @Schema(description = "The Lecturer's e-mail address", example = "bobek.ludwig@ceu.com")
    private String email;

}
