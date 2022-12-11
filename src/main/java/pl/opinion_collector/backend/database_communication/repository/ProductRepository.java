package pl.opinion_collector.backend.database_communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.User;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findBySku(String sku);

    List<Product> findAllByVisibleTrue();

    void deleteAllBySku(String sku);

    @Modifying
    @Query("update Product p set p.authorId = ?1, p.name = ?3, p.pictureUrl = ?4, p.description = ?5, p.visible = ?6 where p.sku = ?2")
    void updateProduct(User authorId, String sku, String name, String pictureUrl, String description, Boolean visible);
}
