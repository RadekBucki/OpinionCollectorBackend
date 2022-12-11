package pl.opinion_collector.backend.logic.product.controller.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * POJO Category Args represented by class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryArg {
    @ApiModelProperty(notes = "Category name", example = "winter", required = true)
    @Size(min = 3, max = 30, message = "The category name does not meet the text length requirements")
    @NotNull(message = "The category name cannot be empty")
    private String name;
    @ApiModelProperty(notes = "category visibility", example = "true", required = true)
    @NotNull(message = "category visibility status must be specified")
    private Boolean visible;
}
