package pl.opinion_collector.backend.database_communication.communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.opinion_collector.backend.database_communication.listener.ProductListener;
import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.database_communication.repository.ProductRepository;
import pl.opinion_collector.backend.database_communication.utils.EntityPreUpdater;

import java.util.List;
import java.util.Objects;

@Component
@Transactional
public class ProductDatabaseCommunication {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryDatabaseCommunication categoryDatabaseCommunication;

    @Autowired
    private UserDatabaseCommunication userDatabaseCommunication;

    @Autowired
    private EntityPreUpdater<Product> entityPreUpdater;

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

        List<Product> filteredProducts = getAllProducts();
        ProductListener productListener = new ProductListener();

        // Make sure that avg opinion is calculated
        filteredProducts.forEach(productListener::calculateAvgOpinion);

        if (categoryName != null) {
            Category category = categoryDatabaseCommunication.getCategoryByName(categoryName);
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getCategories().contains(category))
                    .toList();
        }
        if (searchPhrase != null) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getName().toLowerCase().contains(searchPhrase.toLowerCase()))
                    .toList();
        }

        Double minAvg = Objects.requireNonNullElse(opinionAvgMin, 0.0);
        Double maxAvg = Objects.requireNonNullElse(opinionAvgMax, 5.0);

        filteredProducts = filteredProducts.stream()
                .filter(product -> ((product.getOpinionAvg() >= minAvg) && (product.getOpinionAvg() <= maxAvg)))
                .toList();

        return filteredProducts;
    }

    public Product createProduct(Long authorId, String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        User author = userDatabaseCommunication.getUserById(authorId);
        Product product = new Product(sku, name, pictureUrl, description, visible, author, mapNamesToCategories(categoryNames));
        return productRepository.save(product);
    }

    public void updateProduct(Long authorId, String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        entityPreUpdater.saveOldData(getProductBySku(sku));
        List<Category> categories = mapNamesToCategories(categoryNames);
        Product product = getProductBySku(sku);
        product.getCategories().clear();
        product.getCategories().addAll(categories);
        productRepository.save(product);
        User author = userDatabaseCommunication.getUserById(authorId);
        productRepository.updateProduct(author, sku, name, pictureUrl, description, visible);
    }

    public void removeProduct(String sku) {
        entityPreUpdater.saveOldData(getProductBySku(sku));
        productRepository.deleteAllBySku(sku);
    }

    private List<Category> mapNamesToCategories(List<String> categoryNames) {
        return categoryNames.stream()
                .map(categoryName -> {
                    Category category = categoryDatabaseCommunication.getCategoryByName(categoryName);
                    if (category == null) {
                        return categoryDatabaseCommunication.createCategory(categoryName, true);
                    }
                    return category;
                })
                .toList();
    }
}
