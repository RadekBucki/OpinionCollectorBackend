package pl.opinion_collector.backend.logic.product.service.wrapper;

import lombok.Builder;
import lombok.Data;
import pl.opinion_collector.backend.database_communication.model.Product;

import java.util.List;

@Data
@Builder
public class ProductWrapper {
    private List<Product> products;
    private String actualPage;
    private String numberOfPages;
}
