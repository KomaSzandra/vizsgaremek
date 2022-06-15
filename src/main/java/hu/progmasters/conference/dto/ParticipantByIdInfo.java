package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ParticipantByIdInfo {

    @Schema(description = "Id of the participant")
    private Integer id;

    @Schema(description = "The participant's date of birth", type = "string", format = "date", example = "1964-04-20")
    private LocalDate dateOfBirth;

    @Schema(description = "Name of the participant", example = "Dr. Jack Doe")
    private String name;

    @Schema(description = "Name of the institution", example = "Wigner Adatközpont")
    private String institution;

    @Schema(description = "E-mail address", example = "doedr@participant.com")
    private String email;

    @Schema(description = "Rank of the lecturer")
    private AcademicRank academicRank;

}
