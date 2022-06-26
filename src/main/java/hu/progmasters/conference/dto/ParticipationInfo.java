package hu.progmasters.conference.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationInfo {

    @Schema(description = "Id of the participation", example = "1")
    private Integer id;

    @Schema(description = "The date of application", example = "1")
    private LocalDateTime registration;

    @Schema(description = "Info about the participant")
    private ParticipantInfo participant;

    @Schema(description = "Info about the presentation")
    private PresentationInfo presentation;
}
