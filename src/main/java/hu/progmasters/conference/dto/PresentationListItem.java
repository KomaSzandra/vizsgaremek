package hu.progmasters.conference.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PresentationListItem {

    @Schema(description = "Id of the presentation", example = "1")
    private Integer id;

    @Schema(description = "Id of the lecturer", example = "1")
    private Integer lecturerId;

    @Schema(description = "Name of the lecturer", example = "Dr. Joe Doe")
    private String lecturerName;

    @Schema(description = "Title of the presentation", example = "Researchers in the cloud")
    private String title;

    @Schema(description = "The start time of the presentation", example ="2022-09-26T10:00:00.000Z" )
    private LocalDateTime startTime;

}
