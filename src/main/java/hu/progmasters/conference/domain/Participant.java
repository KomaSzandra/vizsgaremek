package hu.progmasters.conference.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer id;
    @Column(name = "participant_name")
    private String name;
    @Column(name = "institution")
    private String institution;
    @ManyToOne
    @JoinColumn(name = "presentation_presentation_id")
    private Presentation presentation;
}
