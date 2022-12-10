package pl.opinion_collector.backend.logic.opinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.logic.opinion.model.Opinion;

import java.util.List;
import java.util.stream.Collectors;

@Component
class OpinionService implements Opinions {

    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public List<Opinion> getProductOpinions(String sku) {
        return databaseCommunication.getProductOpinions(sku).stream().map(Opinion::new).collect(Collectors.toList());
    }

    @Override
    public Opinion addProductOpinion(Long userId, String sku, Integer opinionValue, String opinionDescription,
                                     String opinionPicture, List<String> advantages, List<String> disadvantages) {

        return new Opinion(databaseCommunication.addProductOpinion(opinionValue, opinionDescription, opinionPicture,
                advantages, disadvantages, sku, userId));
    }

    @Override
    public List<Opinion> getUserOpinions(Long userId) {
        return databaseCommunication.getUserOpinions(userId).stream().map(Opinion::new).collect(Collectors.toList());
    }
}
