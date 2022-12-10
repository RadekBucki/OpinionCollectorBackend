package pl.opinion_collector.backend.logic.product.controller.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryArg {
    @ApiModelProperty(notes = "Category name", example = "winter", required = true)
    private String name;
    @ApiModelProperty(notes = "category visibility", example = "true", required = true)
    private Boolean visible;
}
