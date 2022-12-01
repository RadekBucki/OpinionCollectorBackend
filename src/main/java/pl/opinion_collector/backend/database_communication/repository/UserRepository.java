package pl.opinion_collector.backend.database_communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.opinion_collector.backend.database_communication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
