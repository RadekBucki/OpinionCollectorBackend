package pl.opinion_collector.backend.database_communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.opinion_collector.backend.database_communication.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category getCategoryByCategoryName(String categoryName);

    Category deleteByCategoryName(String categoryName);

}
