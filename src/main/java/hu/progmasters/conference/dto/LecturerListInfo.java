package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerListInfo {

    @Schema(description = "Id of the lecturer", example = "1")
    private Integer id;

    @Schema(description = "Name of the lecturer", example = "Dr. John Doe")
    private String name;

    @Schema(description = "Rank of the lecturer")
    private AcademicRank academicRank;

    @Schema(description = "The institution which employs the lecturer", example = "Central European University")
    private String institution;

    @Schema(description = "Lecturer's email address", example = "doedr@lecturer.com")
    private String email;

}
