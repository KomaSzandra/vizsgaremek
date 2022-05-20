package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ParticipantCreateCommand {

    @NotBlank(message = "must not be blank")
    private String name;

    @Email(message = "must be an email")
    @NotNull(message = "must not be null")
    private String email;

    @NotNull(message = "must not be null")
    private Integer conferenceId;
}
