package pl.opinion_collector.backend.logic.opinion.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.opinion_collector.backend.database_communication.model.Opinion;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Helper DTO class.
 * <p>
 * Mapped from database Opinion class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OpinionDto {

    @ApiModelProperty(notes = "Product identifier", example = "sku123")
    private String sku;
    @ApiModelProperty(notes = "Product name", example = "Samsung Galaxy S20")
    private String productName;
    @ApiModelProperty(notes = "Opinion value, grade", example = "1")
    @Min(value = 1, message = "Opinion cannot be lower than 1")
    @Max(value = 5, message = "Opinion cannot be higher than 5")
    private Integer opinionValue;
    @ApiModelProperty(notes = "Content of opinion", example = "dont like it at all")
    private String description;
    @ApiModelProperty(notes = "URL of opinion picture", example = "www.website.com/picture?id=24532")
    private String pictureUrl;
    @ApiModelProperty(notes = "List of advantages")
    private List<String> advantages;
    @ApiModelProperty(notes = "List of disadvantages")
    private List<String> disadvantages;


    /**
     * Creates OpinionDto from Opinion
     *
     * @param opinion - opinion to be mapped to dto
     * @return mapped dto
     */
    public static OpinionDto map(Opinion opinion) {
        return new OpinionDto(opinion.getProductId().getSku(), opinion.getProductId().getName(), opinion.getOpinionValue(),
                opinion.getDescription(), opinion.getPictureUrl(), opinion.getAdvantages(), opinion.getDisadvantages());
    }

}