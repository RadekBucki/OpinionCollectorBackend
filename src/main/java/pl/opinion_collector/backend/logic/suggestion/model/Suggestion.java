package pl.opinion_collector.backend.logic.suggestion.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Suggestion {

    private Long suggestionId;
    private String description;
    private Long userId;
    private Long productId;
    private String sku;
    private Long reviewerId;
    private Long reviewId;

    public Suggestion(pl.opinion_collector.backend.database_communication.model.Suggestion suggestion) {
        this.suggestionId = suggestion.getSuggestionId();
        this.description = suggestion.getDescription();
        this.userId = suggestion.getUserId().getUserId();
        this.productId = suggestion.getProductId().getProductId();
        this.sku = suggestion.getProductId().getSku();
        this.reviewerId = suggestion.getReviewerId().getUserId();
        this.reviewId = suggestion.getReview().getReviewId();
    }
}
