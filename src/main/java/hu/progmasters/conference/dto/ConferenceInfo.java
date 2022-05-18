package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ConferenceInfo {
    private Integer id;
    private String location;
    private String title;
    private LocalDate localDate;
    private List<PresentationInfo> presentations;
}
