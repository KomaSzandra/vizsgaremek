package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerInfo {
    private Integer id;
    private LocalDate dateOfBirth;
    private String name;
    private AcademicRank academicRank;
    private String institution;
    private String email;
}
