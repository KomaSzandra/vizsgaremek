package hu.progmasters.conference.dto.command;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ParticipationUpdateCommand {

    @NotNull(message = "Must not be null")
    private Integer presentationId;
}
