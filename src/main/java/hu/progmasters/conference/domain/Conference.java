package hu.progmasters.conference.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Conference {
    private Integer id;
    private String location;
    private String title;
    private LocalDate localDate;
    private List<Presentation> presentations;
}
