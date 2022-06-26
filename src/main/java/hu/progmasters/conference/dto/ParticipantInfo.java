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
public class ParticipantInfo {

    @Schema(description = "Id of the participant")
    private Integer id;

    @Schema(description = "Name of the participant", example = "Dr. Jack Doe")
    private String name;

    @Schema(description = "The institution which employs the participant", example = "Wigner Adatközpont")
    private String institution;

    @Schema(description = "E-mail address", example = "doedr@participant.com")
    private String email;

    @Schema(description = "Rank of the lecturer")
    private AcademicRank academicRank;

    @Schema(description = "The participant's date of birth", type = "string", format = "date", example = "1964-04-20")
    private LocalDate dateOfBirth;

}
