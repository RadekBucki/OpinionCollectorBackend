package pl.opinion_collector.backend.logic.opinion;

import pl.opinion_collector.backend.database_communication.model.Opinion;

import java.util.List;

public interface OpinionFacade {
    List<Opinion> getProductOpinions(String sku);

    Opinion addProductOpinion(
            Integer opinionValue,
            String opinionDescription,
            String opinionPicture,
            List<String> advantages, List<String> disadvantages
    );

    List<Opinion> getUserOpinions(Integer userId);
}
