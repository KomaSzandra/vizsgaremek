package hu.progmasters.conference.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerUpdateCommand {

    private Integer lecturerId;
}
