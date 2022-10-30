package pl.opinion_collector.backend.logic.product;

import pl.opinion_collector.backend.database_communication.Category;
import pl.opinion_collector.backend.database_communication.Product;

import java.util.List;

public interface ProductFacade {
    Product getProductBySku(String sku);

    List<Product> getAllProducts();

    List<Product> getProducts();

    List<Product> getProductsFiltered(
            String categoryName,
            String searchPhrase,
            Integer opinionAvgMin,
            Integer opinionAvgMax
    );

    List<Product> addProduct(
            String sku,
            String name,
            String pictureUrl,
            String description,
            List<String> categoryNames,
            Boolean visible
    );

    Product editProduct(
            String sku,
            String name,
            String pictureUrl,
            String description,
            List<String> categoryNames,
            Boolean visible
    );

    void removeProduct(String sku);

    Category addCategory(String categoryName, Boolean visible);

    Category editCategory(String categoryName, Boolean visible);

    void removeCategory(String categoryName);
}
