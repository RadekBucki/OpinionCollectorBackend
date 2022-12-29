package pl.opinion_collector.backend.logic.suggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.opinion_collector.backend.database_communication.model.Review;

@AllArgsConstructor
@Getter
@Setter
public class ReviewDTO {
    private String status;
    private String reply;

    public static ReviewDTO map(Review review) {
        return (review != null) ? new ReviewDTO(review.getStatus(), review.getReply()) : null;
    }
}