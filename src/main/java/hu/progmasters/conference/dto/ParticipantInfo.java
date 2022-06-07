package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ParticipantInfo {
    private Integer id;
    private String name;
    private String institution;
    private String email;
    private AcademicRank academicRank;
    private LocalDate dateOfBirth;
    private PresentationInfo presentation;
}
