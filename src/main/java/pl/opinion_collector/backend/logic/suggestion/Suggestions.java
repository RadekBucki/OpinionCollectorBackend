package pl.opinion_collector.backend.logic.suggestion;


import pl.opinion_collector.backend.logic.suggestion.dto.SuggestionDto;

import java.util.List;

public interface Suggestions {

    List<SuggestionDto> getUserSuggestions(Long userId);

    SuggestionDto addSuggestion(Long userId, String sku, String suggestionDescription);

    List<SuggestionDto> getAllSuggestions();

    SuggestionDto replySuggestion(Long reviewerId, Integer suggestionId, String suggestionStatus, String suggestionReply);
}
