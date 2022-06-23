package hu.progmasters.conference.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationInfo {
    private Integer id;

    private LocalDateTime registration;

    private ParticipantInfo participant;

    private PresentationInfo presentation;
}
