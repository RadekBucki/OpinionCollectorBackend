package pl.opinion_collector.backend.database_communication.communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.repository.OpinionRepository;

import java.util.List;

@Service
public class OpinionDatabaseCommunication {

    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private ProductDatabaseCommunication productDatabaseCommunication;

    @Autowired
    private UserDatabaseCommunication userDatabaseCommunication;

    public List<Opinion> getProductOpinions(String sku) {
        return opinionRepository.findByProductId(productDatabaseCommunication.getProductBySku(sku));
    }

    public Opinion addProductOpinion(Integer opinionValue, String opinionDescription, String opinionPicture, List<String> advantages, List<String> disadvantages) {
        Opinion opinion = new Opinion(opinionValue, opinionDescription, opinionPicture);
        return opinionRepository.save(opinion);
    }

    public List<Opinion> getUserOpinions(Long userId) {
        return opinionRepository.findByUserId(userDatabaseCommunication.getUserById(userId));
    }


}
