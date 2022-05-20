package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ParticipantCreateCommand {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Integer conferenceId;
}
