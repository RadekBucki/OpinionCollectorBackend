package pl.opinion_collector.backend.logic.opinion.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.opinion_collector.backend.logic.opinion.model.Opinion;

import java.util.List;

/**
 * Helper DTO class.
 *
 * Mapped from Opinion class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OpinionShortDto {
    @ApiModelProperty(notes = "Product identifier", example = "sku123", required = true)
    private String sku;
    @ApiModelProperty(notes = "Opinion value, grade", example = "1", required = true)
    private Integer opinionValue;
    @ApiModelProperty(notes = "Content of opinion", example = "dont like it at all", required = true)
    private String description;
    @ApiModelProperty(notes = "URL of opinion picture", example = "1")
    private String pictureUrl;
    @ApiModelProperty(notes = "List of advantages")
    private List<String> advantages;
    @ApiModelProperty(notes = "List of disadvantages")
    private List<String> disadvantages;


    /**
     * Creates OpinionShortDto from Opinion
     * @param opinion - opinion to be mapped to dto
     * @return mapped dto
     */
    public static OpinionShortDto map(Opinion opinion) {
        return new OpinionShortDto(opinion.getSku(),
                opinion.getOpinionValue(), opinion.getDescription(),
                opinion.getPictureUrl(), opinion.getAdvantages(), opinion.getDisadvantages());
    }

}