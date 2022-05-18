package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantInfo {
    private Integer id;
    private String name;
    private String email;
    private ConferenceInfo conferences;
}
