package hu.progmasters.conference.dto.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PresentationCreateCommand {

    @NotBlank(message = "must not be blank")
    @Schema(description = "Title of the presentation", example = "Researchers in the cloud")
    private String title;

    @NotNull(message = "must not be null")
    @Future(message = "must be in the future")
    @Schema(description = "The start time of the presentation", example ="2022-09-26T10:00:00.000Z" )
    private LocalDateTime startTime;
}
