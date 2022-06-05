package hu.progmasters.conference.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.Before;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ParticipantCreateCommand {

    @NotBlank(message = "must not be blank")
    @Schema(description = "Name of the participant", example = "John P. Doe")
    private String name;

    @NotBlank(message = "must not be blank")
    @Schema(description = "Name of the institution", example = "Wigner Adatközpont")
    private String institution;

    @NotNull
    @Schema(description = "Date of the registration")
    private LocalDateTime registration;
}
