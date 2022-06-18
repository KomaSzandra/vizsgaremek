package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantListItem {

    private Integer id;

    @Schema(description = "Name of the participant", example = "Dr. Jack Doe")
    private String name;

    @Schema(description = "Name of the institution", example = "Wigner Adatközpont")
    private String institution;

    @Schema(description = "Rank of the lecturer")
    private AcademicRank academicRank;
}
