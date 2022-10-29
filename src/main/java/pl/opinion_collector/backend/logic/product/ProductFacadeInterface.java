package pl.opinion_collector.backend.logic.product;

import pl.opinion_collector.backend.database_communication.CategoryInterface;
import pl.opinion_collector.backend.database_communication.ProductInterface;

import java.util.List;

public interface ProductFacadeInterface {
    ProductInterface getProductBySku(String sku);

    List<ProductInterface> getAllProducts();

    List<ProductInterface> getProducts();

    List<ProductInterface> getProductsFiltered(
            String categoryName,
            String searchPhrase,
            Integer opinionAvgMin,
            Integer opinionAvgMax
    );

    List<ProductInterface> addProduct(
            String sku,
            String name,
            String pictureUrl,
            String description,
            List<String> categoryNames,
            Boolean visible
    );

    ProductInterface editProduct(
            String sku,
            String name,
            String pictureUrl,
            String description,
            List<String> categoryNames,
            Boolean visible
    );

    void removeProduct(String sku);

    CategoryInterface addCategory(String categoryName, Boolean visible);

    CategoryInterface editCategory(String categoryName, Boolean visible);

    void removeCategory(String categoryName);
}
