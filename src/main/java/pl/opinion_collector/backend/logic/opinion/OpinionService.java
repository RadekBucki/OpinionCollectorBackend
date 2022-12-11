package pl.opinion_collector.backend.logic.opinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.logic.opinion.dto.OpinionDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
class OpinionService implements Opinions {

    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public List<OpinionDto> getProductOpinions(String sku) {
        return databaseCommunication.getProductOpinions(sku).stream().map(OpinionDto::map).collect(Collectors.toList());
    }

    @Override
    public OpinionDto addProductOpinion(Long userId, String sku, Integer opinionValue, String opinionDescription,
                                     String opinionPicture, List<String> advantages, List<String> disadvantages) {

        return OpinionDto.map(databaseCommunication.addProductOpinion(opinionValue, opinionDescription, opinionPicture,
                advantages, disadvantages, sku, userId));
    }

    @Override
    public List<OpinionDto> getUserOpinions(Long userId) {
        return databaseCommunication.getUserOpinions(userId).stream().map(OpinionDto::map).collect(Collectors.toList());
    }
}
