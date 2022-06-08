package hu.progmasters.conference.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentationUpdateCommand {

    @NotNull(message = "must not be null")
    @Future(message = "must be future date")
    @Schema(description = "The start time of the presentation", example ="2022-09-26T10:00:00.000Z" )
    private LocalDateTime startTime;
}
