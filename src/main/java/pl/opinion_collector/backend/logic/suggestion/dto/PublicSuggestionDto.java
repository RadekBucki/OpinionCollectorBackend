package pl.opinion_collector.backend.logic.suggestion.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Helper dto class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublicSuggestionDto {


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
     * Creates PublicSuggestionDto from SuggestionDTO
     *
     * @param dto - suggestion dto to be mapped to another dto
     * @return mapped dto
     */
    public static PublicSuggestionDto map(SuggestionDto dto) {

        String description = dto.getDescription();
        ProductDto productDTO = dto.getProduct();
        ReviewerDto reviewerDTO = dto.getReviewer();
        ReviewDto reviewDTO = dto.getReview();
        UserDto userDTO = dto.getUser();

        return new PublicSuggestionDto(description, productDTO, reviewerDTO, reviewDTO, userDTO);
    }
}
