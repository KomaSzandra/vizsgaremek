package hu.progmasters.conference.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecturer {
    private Integer id;
    private String name;
    private String institution;
    // private Presentation presentation;
}
