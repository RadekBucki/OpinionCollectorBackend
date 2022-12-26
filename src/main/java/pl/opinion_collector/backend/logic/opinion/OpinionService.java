package pl.opinion_collector.backend.logic.opinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.logic.exception.type.InvalidDataIdException;
import pl.opinion_collector.backend.logic.opinion.dto.OpinionDto;

import java.util.List;

@Component
class OpinionService implements Opinions {

    private static final  String INVALID_SKU = "Product sku is invalid";

    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public List<OpinionDto> getProductOpinions(String sku) {
        try {
            return databaseCommunication.getProductOpinions(sku).stream().map(OpinionDto::map).toList();
        } catch (NullPointerException e) {
            throw new InvalidDataIdException(INVALID_SKU);
        }
    }

    @Override
    public OpinionDto addProductOpinion(Long userId, String sku, Integer opinionValue, String opinionDescription,
                                     String opinionPicture, List<String> advantages, List<String> disadvantages) {

        try {
            Opinion opinion = databaseCommunication.addProductOpinion(opinionValue, opinionDescription, opinionPicture,
                    advantages, disadvantages, sku, userId);
            return OpinionDto.map(opinion);
        } catch (NullPointerException e) {
            throw new InvalidDataIdException(INVALID_SKU);
        }
    }

    @Override
    public List<OpinionDto> getUserOpinions(Long userId) {
        return databaseCommunication.getUserOpinions(userId).stream().map(OpinionDto::map).toList();
    }
}
