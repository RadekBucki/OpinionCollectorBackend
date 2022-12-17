package pl.opinion_collector.backend.logic.suggestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.logic.suggestion.dto.SuggestionDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SuggestionService implements Suggestions {

    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public List<SuggestionDto> getUserSuggestions(Long userId) {
        return databaseCommunication.getUserSuggestions(userId).stream().map(SuggestionDto::map)
                .collect(Collectors.toList());
    }

    @Override
    public SuggestionDto addSuggestion(Long userId, String sku, String suggestionDescription) {
        return SuggestionDto.map(databaseCommunication.addSuggestion(sku, userId, suggestionDescription));
    }

    @Override
    public List<SuggestionDto> getAllSuggestions() {
        return databaseCommunication.getAllSuggestions().stream().map(SuggestionDto::map).collect(Collectors.toList());
    }

    @Override
    public SuggestionDto replySuggestion(Long reviewerId, Integer suggestionId, String suggestionStatus, String suggestionReply) {
        return SuggestionDto.map(databaseCommunication.replySuggestion(Long.valueOf(suggestionId), reviewerId,
                suggestionStatus, suggestionReply));
    }
}
