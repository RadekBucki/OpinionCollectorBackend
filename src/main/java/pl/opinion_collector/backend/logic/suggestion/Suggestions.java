package pl.opinion_collector.backend.logic.suggestion;


import pl.opinion_collector.backend.logic.suggestion.model.Suggestion;

import java.util.List;

public interface Suggestions {

    List<Suggestion> getUserSuggestions(Long userId);

    Suggestion addSuggestion(Long userId, String sku, String suggestionDescription);

    List<Suggestion> getAllSuggestions();

    Suggestion replySuggestion(Long reviewerId, Integer suggestionId, String suggestionStatus, String suggestionReply);
}
