package pl.opinion_collector.backend.logic.suggestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.database_communication.model.Suggestion;
import pl.opinion_collector.backend.logic.suggestion.dto.SuggestionDto;
import java.util.List;


@Component
public class SuggestionService implements Suggestions {

    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public List<SuggestionDto> getUserSuggestions(Long userId) {
        return databaseCommunication.getUserSuggestions(userId).stream().map(SuggestionDto::map).toList();
    }

    @Override
    public SuggestionDto addSuggestion(Long userId, String sku, String suggestionDescription) {
        return SuggestionDto.map(databaseCommunication.addSuggestion(sku, userId, suggestionDescription));
    }

    @Override
    public List<SuggestionDto> getAllSuggestions() {
        return databaseCommunication.getAllSuggestions().stream().map(SuggestionDto::map).toList();
    }

    @Override
    public SuggestionDto replySuggestion(Long reviewerId, Integer suggestionId, String suggestionStatus, String suggestionReply) {
        Suggestion suggestion = databaseCommunication.replySuggestion(Long.valueOf(suggestionId), reviewerId,
                suggestionStatus, suggestionReply);
        return SuggestionDto.map(suggestion);
    }
}
