package hu.progmasters.conference.dto.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationCreateCommand {

    @NotNull(message = "Must not be null")
    @Schema(description = "Id of the participant")
    private Integer participantId;

    @NotNull(message = "Must not be null")
    @Schema(description = "Id of the presentation")
    private Integer presentationId;

}
