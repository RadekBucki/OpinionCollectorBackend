package pl.opinion_collector.backend.logic.suggestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.database_communication.model.Suggestion;

import java.util.List;

@Component
public class SuggestionService implements Suggestions {

    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public List<Suggestion> getUserSuggestions(Long userId) {
        return databaseCommunication.getUserSuggestions(userId);
    }

    @Override
    public Suggestion addSuggestion(Long userId, String sku, String suggestionDescription) {
        return databaseCommunication.addSuggestion(sku, userId, suggestionDescription);
    }

    @Override
    public List<Suggestion> getAllSuggestions() {
        return databaseCommunication.getAllSuggestions();
    }

    @Override
    public Suggestion replySuggestion(Long reviewerId, Integer suggestionId, String suggestionStatus, String suggestionReply) {
        return databaseCommunication.replySuggestion(reviewerId, Long.valueOf(suggestionId), suggestionReply, suggestionStatus);
    }
}
