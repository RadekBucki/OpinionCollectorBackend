package pl.opinion_collector.backend.logic.opinion.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.opinion_collector.backend.database_communication.model.Opinion;

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

    @ApiModelProperty(notes = "Opinion ID", example = "5")
    private Long opinionId;
    @ApiModelProperty(notes = "Product identifier", example = "sku123")
    private String sku;
    @ApiModelProperty(notes = "Opinion value, grade", example = "1")
    private Integer opinionValue;
    @ApiModelProperty(notes = "Content of opinion", example = "dont like it at all")
    private String description;
    @ApiModelProperty(notes = "URL of opinion picture", example = "www.website.com/picture?id=24532")
    private String pictureUrl;
    @ApiModelProperty(notes = "List of advantages")
    private List<String> advantages;
    @ApiModelProperty(notes = "List of disadvantages")
    private List<String> disadvantages;
    @ApiModelProperty(notes = "ID of opinion author", example = "3")
    private Long userId;
    @ApiModelProperty(notes = "ID of a product", example = "1")
    private Long productId;


    /**
     * Creates OpinionDto from Opinion
     *
     * @param opinion - opinion to be mapped to dto
     * @return mapped dto
     */
    public static OpinionDto map(Opinion opinion) {
        return new OpinionDto(opinion.getOpinionId(), opinion.getProductId().getSku(), opinion.getOpinionValue(),
                opinion.getDescription(), opinion.getPictureUrl(), opinion.getAdvantages(), opinion.getDisadvantages(),
                opinion.getUserId().getUserId(), opinion.getProductId().getProductId());
    }

}