package pl.opinion_collector.backend.logic.opinion;

import pl.opinion_collector.backend.logic.opinion.model.Opinion;

import java.util.List;

public interface Opinions {
    List<Opinion> getProductOpinions(String sku);

    Opinion addProductOpinion(
            Long userId,
            String sku,
            Integer opinionValue,
            String opinionDescription,
            String opinionPicture,
            List<String> advantages, List<String> disadvantages
    );

    List<Opinion> getUserOpinions(Long userId);
}
