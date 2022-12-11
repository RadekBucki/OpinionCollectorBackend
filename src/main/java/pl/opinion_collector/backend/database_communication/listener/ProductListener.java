package pl.opinion_collector.backend.database_communication.listener;

import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.Product;

import javax.persistence.PostUpdate;

public class ProductListener {

    @PostUpdate
    public void calculateAvgOpinion(Product product) {
        product.setOpinionAvg(product.getOpinions()
                .stream()
                .mapToDouble(Opinion::getOpinionValue)
                .average()
                .orElse(0.0));
    }

}
