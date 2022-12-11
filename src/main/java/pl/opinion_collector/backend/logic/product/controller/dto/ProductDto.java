package pl.opinion_collector.backend.logic.product.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import pl.opinion_collector.backend.logic.opinion.dto.OpinionDto;

import java.util.List;

/**
 * Product dto class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    @ApiModelProperty(notes = "product sku", example = "skusku", required = true)
    private String sku;
    @ApiModelProperty(notes = "Product name", example = "Iphone 15", required = true)
    private String name;
    @ApiModelProperty(notes = "Product photo link", example = "link here")
    private String pictureUrl;
    @ApiModelProperty(notes = "Product description", example = "super cool phone can fly!", required = true)
    private String description;
    @ApiModelProperty(notes = "average product rating", example = "10", required = true)
    private Double opinionAvg;
    @ApiModelProperty(notes = "the name of the person who added the product", example = "Will", required = true)
    private String firstName;
    @ApiModelProperty(notes = "product opinions list", example = "See footnote of Opinions")
    private List<OpinionProductDto> opinions;
    @ApiModelProperty(notes = "product categories list", example = "See footnote of Categories", required = true)
    private List<CategoryDto> categories;
}
