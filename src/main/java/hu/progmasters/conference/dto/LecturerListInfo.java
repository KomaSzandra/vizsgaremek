package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerListInfo {
    private Integer id;
    private String name;
    private AcademicRank academicRank;
    private String institution;
}
