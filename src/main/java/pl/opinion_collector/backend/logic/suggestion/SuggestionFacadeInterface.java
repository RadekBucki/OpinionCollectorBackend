package pl.opinion_collector.backend.logic.suggestion;

import pl.opinion_collector.backend.database_communication.ProductInterface;
import pl.opinion_collector.backend.database_communication.SuggestionInterface;

import java.util.List;

public interface SuggestionFacadeInterface {
    List<SuggestionInterface> getUserSuggestions();

    SuggestionInterface addSuggestion(ProductInterface productInterface, String suggestionDescription);

    List<SuggestionInterface> getAllSuggestions();

    SuggestionInterface replySuggestion(Integer suggestionId, String suggestionStatus, String suggestionReply);
}
