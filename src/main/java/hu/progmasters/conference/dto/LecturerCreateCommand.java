package hu.progmasters.conference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LecturerCreateCommand {
    private String name;
    private String institution;
}
