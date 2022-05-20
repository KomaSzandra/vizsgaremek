package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ParticipantUpdateCommand {

    @NotNull
    private Integer conferenceId;
}
