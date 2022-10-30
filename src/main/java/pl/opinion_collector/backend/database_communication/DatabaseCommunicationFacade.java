package pl.opinion_collector.backend.database_communication;

import java.util.List;

interface DatabaseCommunicationFacade {
    List<User> getAllUsers();

    User getUserByToken(String token);

    User createUser(
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String profilePictureUrl,
            Boolean isAdmin
    );

    String getUserToken(String email, String passwordHash);

    String addUserToken(Integer userId, String token);

    User updateUser(
            Integer userId,
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String profilePictureUrl,
            Boolean isAdmin
    );

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
            Integer authorId,
            String sku, String name,
            String pictureUrl,
            String description,
            List<String> categoryNames,
            Boolean visible
    );

    Product updateProduct(
            Integer authorId,
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

    List<Opinion> getUserOpinions(Integer userId);

    List<Suggestion> getAllSuggestions();

    List<Suggestion> getUserSuggestions(Integer userId);

    List<Suggestion> addSuggestion(String sku, Integer userId, String suggestionDescription);

    Suggestion replySuggestion(
            Integer suggestionId,
            Integer suggestionReviewerId,
            String suggestionStatus,
            String suggestionReply
    );

}
