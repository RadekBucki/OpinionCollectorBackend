package pl.opinion_collector.backend.database_communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.opinion_collector.backend.database_communication.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category getCategoryByCategoryName(String categoryName);

    void deleteByCategoryName(String categoryName);

    @Modifying
    @Query("update Category c set c.visible = ?2 where c.categoryName = ?1")
    void updateCategory(String categoryName, Boolean visible);

}
