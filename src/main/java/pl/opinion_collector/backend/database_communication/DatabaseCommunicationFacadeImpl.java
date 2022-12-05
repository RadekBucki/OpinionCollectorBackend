package pl.opinion_collector.backend.database_communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.communication.CategoryDatabaseCommunication;
import pl.opinion_collector.backend.database_communication.communication.OpinionDatabaseCommunication;
import pl.opinion_collector.backend.database_communication.communication.ProductDatabaseCommunication;
import pl.opinion_collector.backend.database_communication.communication.SuggestionDatabaseCommunication;
import pl.opinion_collector.backend.database_communication.communication.UserDatabaseCommunication;
import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.Suggestion;
import pl.opinion_collector.backend.database_communication.model.User;

import java.util.List;

@Component
public class DatabaseCommunicationFacadeImpl implements DatabaseCommunicationFacade {

    @Autowired
    private UserDatabaseCommunication userDatabaseCommunication;

    @Autowired
    private ProductDatabaseCommunication productDatabaseCommunication;

    @Autowired
    private SuggestionDatabaseCommunication suggestionDatabaseCommunication;

    @Autowired
    private OpinionDatabaseCommunication opinionDatabaseCommunication;

    @Autowired
    private CategoryDatabaseCommunication categoryDatabaseCommunication;


    @Override
    public List<User> getAllUsers() {
        return userDatabaseCommunication.getAllUsers();
    }

    @Override
    public User getUserById(Long userId) {
        return userDatabaseCommunication.getUserById(userId);
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String passwordHash, String profilePictureUrl, Boolean isAdmin) {
        return userDatabaseCommunication.createUser(firstName, lastName, email, passwordHash, profilePictureUrl, isAdmin);
    }

    @Override
    public User updateUser(Long userId, String firstName, String lastName, String email, String passwordHash, String profilePictureUrl, Boolean isAdmin) {
        return userDatabaseCommunication.updateUser(userId, firstName, lastName, email, passwordHash, profilePictureUrl, isAdmin);
    }

    @Override
    public Product getProductBySku(String sku) {
        return productDatabaseCommunication.getProductBySku(sku);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDatabaseCommunication.getAllProducts();
    }

    @Override
    public List<Product> getVisibleProducts() {
        return productDatabaseCommunication.getVisibleProducts();
    }

    @Override
    public List<Product> getProductsFilterProducts(String categoryName, String searchPhrase, Integer opinionAvgMin, Integer opinionAvgMax) {
        return productDatabaseCommunication.getProductsFilterProducts(categoryName, searchPhrase, Double.valueOf(opinionAvgMin), Double.valueOf(opinionAvgMax));
    }

    @Override
    public Product createProduct(Long authorId, String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        return productDatabaseCommunication.createProduct(authorId, sku, name, pictureUrl, description, categoryNames, visible);
    }

    @Override
    public Product updateProduct(Long authorId, String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        return productDatabaseCommunication.updateProduct(authorId, sku, name, pictureUrl, description, categoryNames, visible);
    }

    @Override
    public void removeProduct(String sku) {
        productDatabaseCommunication.removeProduct(sku);
    }

    @Override
    public Category createCategory(String categoryName, Boolean visible) {
        return categoryDatabaseCommunication.createCategory(categoryName, visible);
    }

    @Override
    public Category updateCategory(String categoryName, Boolean visible) {
        return categoryDatabaseCommunication.updateCategory(categoryName, visible);
    }

    @Override
    public void removeCategory(String categoryName) {
        categoryDatabaseCommunication.removeCategory(categoryName);
    }

    @Override
    public List<Opinion> getProductOpinions(String sku) {
        return opinionDatabaseCommunication.getProductOpinions(sku);
    }

    @Override
    public Opinion addProductOpinion(Integer opinionValue, String opinionDescription, String opinionPicture, List<String> advantages, List<String> disadvantages) {
        return opinionDatabaseCommunication.addProductOpinion(opinionValue, opinionDescription, opinionPicture, advantages, disadvantages);
    }

    @Override
    public List<Opinion> getUserOpinions(Long userId) {
        return opinionDatabaseCommunication.getUserOpinions(userId);
    }

    @Override
    public List<Suggestion> getAllSuggestions() {
        return suggestionDatabaseCommunication.getAllSuggestions();
    }

    @Override
    public List<Suggestion> getUserSuggestions(Long userId) {
        return suggestionDatabaseCommunication.getUserSuggestions(userId);
    }

    @Override
    public Suggestion addSuggestion(String sku, Long userId, String suggestionDescription) {
        return suggestionDatabaseCommunication.addSuggestion(sku, userId, suggestionDescription);
    }

    @Override
    public Suggestion replySuggestion(Long suggestionId, Long suggestionReviewerId, String suggestionStatus, String suggestionReply) {
        return suggestionDatabaseCommunication.replySuggestion(suggestionId, suggestionReviewerId, suggestionStatus, suggestionReply);
    }
}
