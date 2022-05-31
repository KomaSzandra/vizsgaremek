package hu.progmasters.conference.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerListInfo {
    private Integer id;
    private String name;
    private String academicRank;
    private String institution;
}
