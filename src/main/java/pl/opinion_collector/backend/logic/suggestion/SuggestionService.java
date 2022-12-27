package pl.opinion_collector.backend.logic.suggestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.logic.exception.type.InvalidDataIdException;
import pl.opinion_collector.backend.logic.suggestion.dto.SuggestionDto;
import java.util.List;


@Component
public class SuggestionService implements Suggestions {
    private static final  String INVALID_SKU = "Product sku is invalid";
    private static final  String INVALID_ID = "Provided suggestion id is invalid";

    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public List<SuggestionDto> getUserSuggestions(Long userId) {
        return databaseCommunication.getUserSuggestions(userId).stream().map(SuggestionDto::map).toList();
    }

    @Override
    public SuggestionDto addSuggestion(Long userId, String sku, String suggestionDescription) {
        try {
            return SuggestionDto.map(databaseCommunication.addSuggestion(sku, userId, suggestionDescription));
        } catch (NullPointerException e) {
            throw new InvalidDataIdException(INVALID_SKU);
        }
    }

    @Override
    public List<SuggestionDto> getAllSuggestions() {
        return databaseCommunication.getAllSuggestions().stream().map(SuggestionDto::map).toList();
    }

    @Override
    public SuggestionDto replySuggestion(Long reviewerId, Integer suggestionId, String suggestionStatus, String suggestionReply) {
        try {
            return SuggestionDto.map(databaseCommunication.replySuggestion(Long.valueOf(suggestionId), reviewerId,
                    suggestionStatus, suggestionReply));
        } catch (NullPointerException e) {
            throw new InvalidDataIdException(INVALID_ID);
        }
    }
}
