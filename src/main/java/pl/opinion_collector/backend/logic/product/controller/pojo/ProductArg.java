package pl.opinion_collector.backend.logic.product.controller.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * POJO Product Args represented by class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductArg {
    @ApiModelProperty(notes = "the id of the person who added the product", example = "123", required = true)
    private Long authorId;
    @ApiModelProperty(notes = "product sku", example = "skusku", required = true)
    private String sku;
    @ApiModelProperty(notes = "Product name", example = "Iphone 15", required = true)
    private String name;
    @ApiModelProperty(notes = "Product photo link", example = "link here")
    private String pictureUrl;
    @ApiModelProperty(notes = "Product description", example = "super cool phone can fly!", required = true)
    private String description;
    @ApiModelProperty(notes = "product categories list", example = "See footnote of Categories", required = true)
    private List<String> categoryNames;
    @ApiModelProperty(notes = "Product visible status", example = "true")
    private Boolean visible;
}
