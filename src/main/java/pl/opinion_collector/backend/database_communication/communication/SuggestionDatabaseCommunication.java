package pl.opinion_collector.backend.database_communication.communication;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.Review;
import pl.opinion_collector.backend.database_communication.model.Suggestion;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.database_communication.repository.SuggestionRepository;

import java.util.List;

@Service
@NoArgsConstructor
public class SuggestionDatabaseCommunication {

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private UserDatabaseCommunication userDatabaseCommunication;

    @Autowired
    private ProductDatabaseCommunication productDatabaseCommunication;

    public List<Suggestion> getAllSuggestions() {
        return suggestionRepository.findAll();
    }

    public List<Suggestion> getUserSuggestions(Long userId) {
        User user = userDatabaseCommunication.getUserById(userId);
        return suggestionRepository.findAllByUserId(user);
    }

    public Suggestion addSuggestion(String sku, Long userId, String suggestionDescription) {
        User user = userDatabaseCommunication.getUserById(userId);
        Product product = productDatabaseCommunication.getProductBySku(sku);
        Suggestion suggestion = new Suggestion(suggestionDescription, user, product);
        return suggestionRepository.save(suggestion);
    }

    public Suggestion replySuggestion(Long suggestionId, Long suggestionReviewerId, String suggestionStatus, String suggestionReply) {
        Suggestion suggestion = suggestionRepository.getReferenceById(suggestionId);
        User reviewer = userDatabaseCommunication.getUserById(suggestionReviewerId);
        Review review = new Review(suggestionStatus, suggestionReply);
        suggestion.setReviewerId(reviewer);
        suggestion.setReview(review);
        return suggestionRepository.save(suggestion);
    }


}
