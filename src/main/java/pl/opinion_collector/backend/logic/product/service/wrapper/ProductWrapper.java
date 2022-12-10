package pl.opinion_collector.backend.logic.product.service.wrapper;

import lombok.*;
import pl.opinion_collector.backend.database_communication.model.Product;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductWrapper {
    private List<Product> products;
    private int actualPage;
    private int numberOfPages;
}
