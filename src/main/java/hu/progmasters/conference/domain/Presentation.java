package hu.progmasters.conference.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @OneToOne
    @JoinColumn(name = "lecturer_lecturer_id")
    private Lecturer lecturer;
    @Column(name = "presentation_name")
    private String title;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @OneToMany(mappedBy = "presentation")
    private List<Participant> participants;

}
