package pl.opinion_collector.backend.logic.opinion;

import pl.opinion_collector.backend.database_communication.OpinionInterface;

import java.util.List;

public interface OpinionFacadeInterface {
    List<OpinionInterface> getProductOpinions(String sku);

    OpinionInterface addProductOpinion(
            Integer opinionValue,
            String opinionDescription,
            String opinionPicture,
            List<String> advantages, List<String> disadvantages
    );

    List<OpinionInterface> getUserOpinions(Integer userId);
}
