package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PresentationCreateCommand {

    private Integer lecturerId;

    @NotBlank(message = "must not be blank")
    private String title;

    @NotNull(message = "must not be null")
    @Future(message = "must be in the future")
    private LocalDateTime startTime;

    @Min(value = 10, message = "minimum 10")
    private int maxParticipants;
}
