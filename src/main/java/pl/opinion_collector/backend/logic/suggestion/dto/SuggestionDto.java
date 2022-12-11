package pl.opinion_collector.backend.logic.suggestion.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.opinion_collector.backend.database_communication.model.Suggestion;


/**
 * Helper classes used to avoid dumping huge JSON onto frontend
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuggestionDto {
    @ApiModelProperty(notes = "ID of suggestion", example = "1")
    private Long suggestionId;
    @ApiModelProperty(notes = "Suggestion reviewer id", example = "5")
    private Long reviewId;
    @ApiModelProperty(notes = "ID of user that created suggestion", example = "2")
    private Long userId;
    @ApiModelProperty(notes = "Content of suggestion", example = "change the color of the toy!",
            required = true)
    private String description;
    @ApiModelProperty(notes = "ID of product addressed by suggestion", example = "1")
    private Long productId;
    @ApiModelProperty(notes = "ID of suggestion reviewer", example = "1")
    private Long reviewerId;
    @ApiModelProperty(notes = "identifier of a product", example = "skuu123")
    private String sku;

    /**
     * Creates SuggestionDto from Suggestion
     * @param suggestion - suggestion to be mapped to dto
     * @return mapped dto
     */
    public static SuggestionDto map(Suggestion suggestion) {
        return new SuggestionDto(suggestion.getSuggestionId(), suggestion.getReviewerId().getUserId(),
                suggestion.getUserId().getUserId(), suggestion.getDescription(), suggestion.getProductId().getProductId(),
                suggestion.getReviewerId().getUserId(), suggestion.getProductId().getSku());
    }
}