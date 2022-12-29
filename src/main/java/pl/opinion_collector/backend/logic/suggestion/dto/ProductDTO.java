package pl.opinion_collector.backend.logic.suggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.opinion_collector.backend.database_communication.model.Product;

@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private String sku;
    private String name;
    private String pictureUrl;
    private String description;

    public static ProductDTO map(Product product) {
        return new ProductDTO(product.getSku(), product.getName(), product.getPictureUrl(), product.getDescription());
    }
}
