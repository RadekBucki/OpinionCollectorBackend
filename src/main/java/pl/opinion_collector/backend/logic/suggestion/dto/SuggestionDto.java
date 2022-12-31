package pl.opinion_collector.backend.logic.suggestion.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import pl.opinion_collector.backend.database_communication.model.*;


/**
 * Helper classes used to avoid dumping huge JSON onto frontend
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuggestionDto {

    @ApiModelProperty(notes = "ID of suggestion", example = "1")
    Long suggestionId;
    @ApiModelProperty(notes = "Content of suggestion", example = "change the color of the toy!",
            required = true)
    String description;
    @ApiModelProperty(notes = "Representation of product addressed by suggestion", example = """
            "product": {
                  "sku": "skusku",
                  "name": "Samsung Galaxy S20",
                  "pictureUrl": "www.picture.url"
                }
            """)
    ProductDto product;
    @ApiModelProperty(notes = "Representation of reviewer of suggestion", example = """
            "reviewer": {
                  "firstName": "John",
                  "lastName": "Smith",
                  "profilePictureUrl": "https://pl.pinterest.com/pin/327848047887112192/"
                 }
            """)
    ReviewerDto reviewer;
    @ApiModelProperty(notes = "Representation of review of suggestion", example = """
             "review": {
                   "reply": "Reply message",
                   "status": reply status
                 }
            """)
    ReviewDto review;
    @ApiModelProperty(notes = "Representation of a user that created suggestion", example = """
            "user": {
                  "firstName": "John",
                  "lastName": "Smith",
                  "profilePictureUrl": "https://pl.pinterest.com/pin/327848047887112192/"
                 }
            """)
    UserDto user;

    /**
     * Creates SuggestionDto from Suggestion
     *
     * @param suggestion - suggestion to be mapped to dto
     * @return mapped dto
     */
    public static SuggestionDto map(Suggestion suggestion) {
        Long suggestionId = suggestion.getSuggestionId();
        String description = suggestion.getDescription();
        ProductDto productDTO = ProductDto.map(suggestion.getProductId());
        ReviewerDto reviewerDTO = ReviewerDto.map(suggestion.getReviewerId());
        ReviewDto reviewDTO = ReviewDto.map(suggestion.getReview());
        UserDto userDTO = UserDto.map(suggestion.getUserId());
        return new SuggestionDto(suggestionId, description, productDTO, reviewerDTO, reviewDTO, userDTO);
    }
}