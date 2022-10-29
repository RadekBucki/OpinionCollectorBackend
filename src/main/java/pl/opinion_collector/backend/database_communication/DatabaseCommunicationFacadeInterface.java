package pl.opinion_collector.backend.database_communication;

import java.util.List;

interface DatabaseCommunicationFacadeInterface {
    List<UserInterface> getAllUsers();

    UserInterface getUserByToken(String token);

    UserInterface createUser(
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String profilePictureUrl,
            Boolean isAdmin
    );

    String getUserToken(String email, String passwordHash);

    String addUserToken(Integer userId, String token);

    UserInterface updateUser(
            Integer userId,
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String profilePictureUrl,
            Boolean isAdmin
    );

    ProductInterface getProductBySku(String sku);

    List<ProductInterface> getAllProducts();

    List<ProductInterface> getVisibleProducts();

    List<ProductInterface> getProductsFilterProducts(
            String categoryName,
            String searchPhrase,
            Integer opinionAvgMin,
            Integer opinionAvgMax
    );

    ProductInterface createProduct(
            Integer authorId,
            String sku, String name,
            String pictureUrl,
            String description,
            List<String> categoryNames,
            Boolean visible
    );

    ProductInterface updateProduct(
            Integer authorId,
            String sku,
            String name,
            String pictureUrl,
            String description,
            List<String> categoryNames,
            Boolean visible
    );

    ProductInterface removeProduct(String sku);

    CategoryInterface createCategory(String categoryName, Boolean visible);

    CategoryInterface updateCategory(String categoryName, Boolean visible);

    CategoryInterface removeCategory(String categoryName);

    List<OpinionInterface> getProductOpinions(String sku);

    OpinionInterface addProductOpinion(
            Integer opinionValue,
            String opinionDescription,
            String opinionPicture,
            List<String> advantages,
            List<String> disadvantages
    );

    List<OpinionInterface> getUserOpinions(Integer userId);

    List<SuggestionInterface> getAllSuggestions();

    List<SuggestionInterface> getUserSuggestions(Integer userId);

    List<SuggestionInterface> addSuggestion(String sku, Integer userId, String suggestionDescription);

    SuggestionInterface replySuggestion(
            Integer suggestionId,
            Integer suggestionReviewerId,
            String suggestionStatus,
            String suggestionReply
    );

}
