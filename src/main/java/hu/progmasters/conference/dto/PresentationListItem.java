package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PresentationListItem {
    private Integer lecturerId;
    private String title;
    private LocalDateTime startTime;
    private int maxParticipants;
}
