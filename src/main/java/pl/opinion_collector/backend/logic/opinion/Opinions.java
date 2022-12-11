package pl.opinion_collector.backend.logic.opinion;

import pl.opinion_collector.backend.logic.opinion.dto.OpinionDto;

import java.util.List;

public interface Opinions {
    List<OpinionDto> getProductOpinions(String sku);

    OpinionDto addProductOpinion(
            Long userId,
            String sku,
            Integer opinionValue,
            String opinionDescription,
            String opinionPicture,
            List<String> advantages, List<String> disadvantages
    );

    List<OpinionDto> getUserOpinions(Long userId);
}
