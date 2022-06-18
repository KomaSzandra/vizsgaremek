package hu.progmasters.conference.dto.command;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.exceptionhandler.Rank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerUpdateCommand {

    private Integer lecturerId;
}
