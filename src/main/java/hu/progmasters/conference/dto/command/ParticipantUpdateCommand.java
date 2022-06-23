package hu.progmasters.conference.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantUpdateCommand {

    @NotBlank(message = "Must not be blank")
    private String institution;
}
