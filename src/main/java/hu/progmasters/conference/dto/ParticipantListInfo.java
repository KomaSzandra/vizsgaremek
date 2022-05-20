package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantListInfo {
    private String name;
    private String email;
}
