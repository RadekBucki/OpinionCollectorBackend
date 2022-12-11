package pl.opinion_collector.backend.database_communication.communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.database_communication.repository.ProductRepository;

import java.util.List;

@Component
@Transactional
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
        //TODO: Allow using only some parameters (some of them are null)
        List<Product> jpaFilteredProducts = productRepository.findAllByNameContainingIgnoreCaseAndOpinionAvgIsBetweenAndVisibleTrue(searchPhrase, opinionAvgMin, opinionAvgMax);
        Category category = categoryDatabaseCommunication.getCategoryByName(categoryName);
        return jpaFilteredProducts.stream()
                .filter(product -> product.getCategories().contains(category)).toList();
    }

    public Product createProduct(Long authorId, String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        //TODO: NOT ALLOW SKU DUPLICATES
        //TODO: NOT ALLOW CATEGORIES DUPLICATES
        User author = userDatabaseCommunication.getUserById(authorId);
        Product product = new Product(sku, name, pictureUrl, description, visible, author, mapNamesToCategories(categoryNames));
        return productRepository.save(product);
    }

    public void updateProduct(Long authorId, String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        //TODO: TO FIX
        User author = userDatabaseCommunication.getUserById(authorId);
        List<Category> categories = mapNamesToCategories(categoryNames);
        productRepository.updateProduct(author, sku, name, pictureUrl, description, visible, categories);
    }

    public void removeProduct(String sku) {
        productRepository.deleteAllBySku(sku);
    }

    private List<Category> mapNamesToCategories(List<String> categoryNames) {
        return categoryNames.stream()
                .map(categoryName -> categoryDatabaseCommunication.createCategory(categoryName, true))
                .toList();
    }


}
