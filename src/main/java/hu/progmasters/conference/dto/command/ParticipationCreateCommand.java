package hu.progmasters.conference.dto.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class ParticipationCreateCommand {

    @NotNull(message = "Must not be null")
    @Schema(description = "Id of participant")
    private Integer participantId;

    @NotNull(message = "Must not be null")
    @Schema(description = "Id of presentation")
    private Integer presentationId;

    @NotNull(message = "Must not be null")
    @Past(message = "Registration deadline has expired!")
    @Schema(description = "Date of the registration")
    private LocalDateTime registration;
}
