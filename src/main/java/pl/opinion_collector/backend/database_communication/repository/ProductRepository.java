package pl.opinion_collector.backend.database_communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.opinion_collector.backend.database_communication.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findBySku(String sku);

    List<Product> findAllByVisibleTrue();

    List<Product> findAllByNameContainingIgnoreCaseAndOpinionAvgIsBetween(
            String searchPhrase, Double opinionAvgMin, Double opinionAvgMax);

    Product deleteBySku(String sku);

}
