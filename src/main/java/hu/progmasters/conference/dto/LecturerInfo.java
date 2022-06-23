package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerInfo {

    @Schema(description = "Id of the Lecturer", example = "1")
    private Integer id;

    @Schema(description = "Name of the Lecturer", example = "Dr. John Doe")
    private String name;

    @Schema(description = "The lecturer's date of birth", format = "date", example = "1964-04-20")
    private LocalDate dateOfBirth;

    @Schema(description = "Name of the Institution", example = "Central European University")
    private String institution;

    @Schema(description = "The Lecturer's e-mail address", example = "ludwig@ceu.com")
    private String email;

    @Schema(description = "Rank of the lecturer")
    private AcademicRank academicRank;

    @Schema(description = "Id of a presenattion")
    private Integer presentationId;

    @Schema(description = "Title of a presentation")
    private String presentationTitle;

}
