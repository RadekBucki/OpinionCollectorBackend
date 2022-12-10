package pl.opinion_collector.backend.logic.suggestion.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.opinion_collector.backend.logic.suggestion.model.Suggestion;


/**
 * Helper classes used to avoid dumping huge JSON onto frontend
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuggestionShortDto {
    @ApiModelProperty(notes = "ID of suggestion", example = "1", required = true)
    private Long suggestionId;
    @ApiModelProperty(notes = "Suggestion reviewer", example = "5", required = true)
    private Long reviewId;
    @ApiModelProperty(notes = "ID of user that created suggestion", example = "2", required = true)
    private Long userId;
    @ApiModelProperty(notes = "Content of suggestion", example = "change the color of the toy!",
            required = true)
    private String description;
    @ApiModelProperty(notes = "ID of product addressed by suggestion", example = "1", required = true)
    private Long productId;
    @ApiModelProperty(notes = "ID of suggestion reviewer", example = "1", required = true)
    private Long reviewerId;

    /**
     * Creates SuggestionShortDto from Suggestion
     * @param suggestion - suggestion to be mapped to dto
     * @return mapped dto
     */
    public static SuggestionShortDto map(Suggestion suggestion) {
        return new SuggestionShortDto(suggestion.getSuggestionId(), suggestion.getReviewId(),
                suggestion.getUserId(), suggestion.getDescription(),
                suggestion.getProductId(), suggestion.getReviewerId());
    }
}