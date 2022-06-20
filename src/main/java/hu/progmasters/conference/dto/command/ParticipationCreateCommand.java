package hu.progmasters.conference.dto.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
public class ParticipationCreateCommand {

    @NotNull(message = "Must not be null")
    @Schema(description = "Id of participant")
    private Integer participantId;

    @NotNull(message = "Must not be null")
    @Schema(description = "Id of presentation")
    private Integer presentationId;

}
