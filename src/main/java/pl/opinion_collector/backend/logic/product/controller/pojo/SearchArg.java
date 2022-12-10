package pl.opinion_collector.backend.logic.product.controller.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POJO search Args represented by class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchArg {
    @ApiModelProperty(notes = "category search parameter", example = "Phone")
    private String categoryName;
    @ApiModelProperty(notes = "search Phrase parameter", example = "Phone")
    private String searchPhrase;
    @ApiModelProperty(notes = "opinion Avg Min parameter", example = "5")
    private Integer opinionAvgMin;
    @ApiModelProperty(notes = "opinion Avg Max parameter", example = "9")
    private Integer opinionAvgMax;
}
