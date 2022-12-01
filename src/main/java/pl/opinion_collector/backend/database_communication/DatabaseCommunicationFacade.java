package pl.opinion_collector.backend.database_communication;

import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.Suggestion;
import pl.opinion_collector.backend.database_communication.model.User;

import java.util.List;

interface DatabaseCommunicationFacade {
    List<User> getAllUsers();

    User getUserById(Long userId);

    User createUser(
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String profilePictureUrl,
            Boolean isAdmin
    );

    User updateUser(Long userId, String firstName, String lastName, String email, String passwordHash, String profilePictureUrl, Boolean isAdmin);

    Product getProductBySku(String sku);

    List<Product> getAllProducts();

    List<Product> getVisibleProducts();

    List<Product> getProductsFilterProducts(
            String categoryName,
            String searchPhrase,
            Integer opinionAvgMin,
            Integer opinionAvgMax
    );

    Product createProduct(
            Long authorId,
            String sku,
            String name,
            String pictureUrl,
            String description,
            List<String> categoryNames,
            Boolean visible
    );

    Product updateProduct(
            Long authorId,
            String sku,
            String name,
            String pictureUrl,
            String description,
            List<String> categoryNames,
            Boolean visible
    );

    Product removeProduct(String sku);

    Category createCategory(String categoryName, Boolean visible);

    Category updateCategory(String categoryName, Boolean visible);

    Category removeCategory(String categoryName);

    List<Opinion> getProductOpinions(String sku);

    Opinion addProductOpinion(
            Integer opinionValue,
            String opinionDescription,
            String opinionPicture,
            List<String> advantages,
            List<String> disadvantages
    );

    List<Opinion> getUserOpinions(Long userId);

    List<Suggestion> getAllSuggestions();

    List<Suggestion> getUserSuggestions(Long userId);

    Suggestion addSuggestion(String sku, Long userId, String suggestionDescription);

    Suggestion replySuggestion(
            Long suggestionId,
            Long suggestionReviewerId,
            String suggestionStatus,
            String suggestionReply
    );

}
