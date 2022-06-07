package hu.progmasters.conference.dto;

import hu.progmasters.conference.domain.AcademicRank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LecturerUpdateCommand {

    @NotBlank(message = "Must not be blank!")
    @Schema(description = "Rank of the lecturer", example = "Full professor")
    private AcademicRank academicRank;
}
