package hu.progmasters.conference.dto.command;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipationUpdateCommand {

    private Integer presentationId;
}
