package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LecturerCreateUpdateCommand {

    @NotBlank
    private String name;

    @NotBlank
    private String institution;
}
