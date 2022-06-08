package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PresentationListItem {
    private Integer id;
    private Integer lecturerId;
    private String title;
    private LocalDateTime startTime;
    private List<ParticipantListItem> participants;
}
