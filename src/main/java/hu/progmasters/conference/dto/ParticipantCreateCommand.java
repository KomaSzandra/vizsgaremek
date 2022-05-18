package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantCreateCommand {
    private String name;
    private String email;
    private Integer conferenceId;
}
