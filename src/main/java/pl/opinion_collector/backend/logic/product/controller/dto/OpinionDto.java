package pl.opinion_collector.backend.logic.product.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OpinionDto {
    @ApiModelProperty(notes = "average opinion rating", example = "6", required = true)
    private Integer opinionValue;
    @ApiModelProperty(notes = "description of the opinion", example = "amazing product recommend!",
            required = true)
    private String description;
    @ApiModelProperty(notes = "Link to photo attached", example = "link here")
    private String pictureUrl;
    @ApiModelProperty(notes = "list of advantages", example = "['cool', 'amazing']")
    private List<String> advantages;
    @ApiModelProperty(notes = "list of disadvantages", example = "['bad', 'uncomfortable']")
    private List<String> disadvantages;
    @ApiModelProperty(notes = "author's name", example = "['bad', 'uncomfortable']", required = true)
    private String firstName;
}
