package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantListItem {
    private String name;
    private String institution;
    private AcademicRank academicRank;
}
