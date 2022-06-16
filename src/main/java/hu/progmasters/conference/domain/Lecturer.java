package hu.progmasters.conference.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lecturer")
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "institution")
    private String institution;

    @Column(name = "academic_rank")
    @Enumerated(EnumType.STRING)
    private AcademicRank academicRank;

    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(mappedBy = "lecturer")
    private Presentation presentation;
}
