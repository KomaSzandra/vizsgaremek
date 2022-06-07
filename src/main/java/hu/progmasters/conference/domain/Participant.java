package hu.progmasters.conference.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "institution")
    private String institution;

    @Column(name = "academic_rank")
    @Enumerated(EnumType.STRING)
    private AcademicRank academicRank;

    private LocalDateTime registration;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name ="email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "presentation_id")
    private Presentation presentation;
}
