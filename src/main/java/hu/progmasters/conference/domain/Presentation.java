package hu.progmasters.conference.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "presentation")
public class Presentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "presentation_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "lecturer_lecturer_id")
    private Lecturer lecturer;
    @Column(name = "presentation_name")
    private String title;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "max_participants")
    private int maxParticipants;
}
