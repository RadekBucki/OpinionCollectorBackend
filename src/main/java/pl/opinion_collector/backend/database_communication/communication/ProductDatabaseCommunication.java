package pl.opinion_collector.backend.database_communication.communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.database_communication.repository.ProductRepository;

import java.util.List;

@Service
public class ProductDatabaseCommunication {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryDatabaseCommunication categoryDatabaseCommunication;

    @Autowired
    private UserDatabaseCommunication userDatabaseCommunication;

    public Product getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getVisibleProducts() {
        return productRepository.findAllByVisibleTrue();
    }

    public List<Product> getProductsFilterProducts(String categoryName, String searchPhrase, Double opinionAvgMin, Double opinionAvgMax) {
        List<Product> jpaFilteredProducts = productRepository.findAllByNameContainingIgnoreCaseAndOpinionAvgIsBetween(searchPhrase, opinionAvgMin, opinionAvgMax);
        Category category = categoryDatabaseCommunication.getCategoryByName(categoryName);
        return jpaFilteredProducts.stream()
                .filter(product -> product.getCategories().contains(category)).toList();
    }

    public Product createProduct(Long authorId, String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        User author = userDatabaseCommunication.getUserById(authorId);
        Product product = new Product(sku, name, pictureUrl, description, visible, author);
        return productRepository.save(product);
    }

    public Product updateProduct(Long authorId, String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        return createProduct(authorId, sku, name, pictureUrl, description, categoryNames, visible);
    }

    public Product removeProduct(String sku) {
        return productRepository.deleteBySku(sku);
    }


}
