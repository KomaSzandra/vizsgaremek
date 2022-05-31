package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.Participant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PresentationInfo {
    private Integer id;
    private LecturerInfo lecturer;
    private String title;
    private LocalDateTime startTime;
    //private List<ParticipantListItem> participants;
}
