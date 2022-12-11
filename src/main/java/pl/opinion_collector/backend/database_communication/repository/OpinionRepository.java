package pl.opinion_collector.backend.database_communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.User;

import java.util.List;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    List<Opinion> findAllByProductId(Product product);

    List<Opinion> findAllByUserId(User user);
}
