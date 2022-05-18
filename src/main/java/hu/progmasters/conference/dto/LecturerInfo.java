package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LecturerInfo {
    private Integer id;
    private String name;
    private String institution;
}
