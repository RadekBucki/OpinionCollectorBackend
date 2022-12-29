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
    ProductDTO product;
    @ApiModelProperty(notes = "Representation of reviewer of suggestion", example = """
            "reviewer": {
                  "firstName": "John",
                  "lastName": "Smith",
                  "profilePictureUrl": "https://pl.pinterest.com/pin/327848047887112192/"
                 }
            """)
    ReviewerDTO reviewer;
    @ApiModelProperty(notes = "Representation of review of suggestion", example = """
             "review": {
                   "reply": "Reply message",
                   "status": reply status
                 }
            """)
    ReviewDTO review;
    @ApiModelProperty(notes = "Representation of a user that created suggestion", example = """
            "user": {
                  "firstName": "John",
                  "lastName": "Smith",
                  "profilePictureUrl": "https://pl.pinterest.com/pin/327848047887112192/"
                 }
            """)
    UserDTO user;

    /**
     * Creates PublicSuggestionDto from SuggestionDTO
     *
     * @param dto - suggestion dto to be mapped to another dto
     * @return mapped dto
     */
    public static PublicSuggestionDto map(SuggestionDto dto) {

        String description = dto.getDescription();
        ProductDTO productDTO = dto.getProduct();
        ReviewerDTO reviewerDTO = dto.getReviewer();
        ReviewDTO reviewDTO = dto.getReview();
        UserDTO userDTO = dto.getUser();

        return new PublicSuggestionDto(description, productDTO, reviewerDTO, reviewDTO, userDTO);
    }
}
