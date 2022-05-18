package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ConferenceCreateCommand {

    @NotBlank(message = "must not be blank")
    private String location;

    @NotBlank(message = "must not be blank")
    private String title;

    @Future(message = "must be in the future")
    @NotNull(message = "must not be null")
    private LocalDate localDate;
}
