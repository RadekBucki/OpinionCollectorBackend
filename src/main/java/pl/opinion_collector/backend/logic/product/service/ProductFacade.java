package pl.opinion_collector.backend.logic.product.service;

import org.springframework.data.domain.Page;
import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.logic.product.service.wrapper.ProductWrapper;

import java.util.List;

public interface ProductFacade {

    Product getProductBySku(String sku);

    List<Product> getAllProducts(String page);

    ProductWrapper getProducts(String page);

    List<Product> getProductsFiltered(
            String categoryName,
            String searchPhrase,
            Integer opinionAvgMin,
            Integer opinionAvgMax
    );

    Product addProduct(
            Long authorId,
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

    List<Category> getCategories();

    List<Category> getAllCategories();
}
