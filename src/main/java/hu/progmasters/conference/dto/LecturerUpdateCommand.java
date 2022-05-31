package hu.progmasters.conference.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LecturerUpdateCommand {

    @NotBlank(message = "Must not be blank!")
    @Schema(description = "Rank of the lecturer", example = "Associate professor")
    private String academicRank;
}
