package pl.opinion_collector.backend.database_communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.opinion_collector.backend.database_communication.model.Suggestion;
import pl.opinion_collector.backend.database_communication.model.User;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    List<Suggestion> findAllByUserId(User userId);

}
