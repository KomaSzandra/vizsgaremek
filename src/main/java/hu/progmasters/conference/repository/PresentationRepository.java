package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

    Presentation findPresentationByTitle(String title);

}
