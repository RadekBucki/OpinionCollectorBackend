package pl.opinion_collector.backend.logic.product.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Category dto class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
    @ApiModelProperty(notes = "category name", example = "banana", required = true)
    private String categoryName;
}
