package pl.opinion_collector.backend.logic.suggestion;

import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.Suggestion;

import java.util.List;

public interface SuggestionFacade {
    List<Suggestion> getUserSuggestions();

    Suggestion addSuggestion(Product product, String suggestionDescription);

    List<Suggestion> getAllSuggestions();

    Suggestion replySuggestion(Integer suggestionId, String suggestionStatus, String suggestionReply);
}
