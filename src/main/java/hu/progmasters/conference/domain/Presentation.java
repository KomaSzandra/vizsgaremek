package hu.progmasters.conference.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Presentation {
    private Integer id;
    private Lecturer lecturer;
    // private String lecturer;
    private String title;
    private LocalDateTime startTime;
    // private List<Participant> participants;
}
