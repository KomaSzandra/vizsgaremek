package hu.progmasters.conference.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ParticipantCreateCommand {

    @NotBlank(message = "must not be blank")
    @Schema(description = "Name of the participant", example = "John P. Doe")
    private String name;

    @NotBlank(message = "must not be blank")
    @Schema(description = "Name of the institution", example = "Wigner Adatközpont")
    private String institution;
}
