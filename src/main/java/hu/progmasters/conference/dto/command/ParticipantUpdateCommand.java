package hu.progmasters.conference.dto.command;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ParticipantUpdateCommand {

    @NotBlank(message = "Must not be blank")
    private String institution;
}
