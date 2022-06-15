package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {


    Presentation findPresentationByTitle(String title);

//    public Presentation findByTitle(String titleToFind) {
//        TypedQuery<Presentation> query = entityManager.createQuery("SELECT p FROM Presentation p " +
//                "WHERE p.title = :titleParam", Presentation.class);
//        query.setParameter("titleParam", titleToFind);
//        Presentation presentationWithTitle = query.getSingleResult();
//        return presentationWithTitle;
}
