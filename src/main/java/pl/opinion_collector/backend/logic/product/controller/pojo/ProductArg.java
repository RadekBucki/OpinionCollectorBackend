package pl.opinion_collector.backend.logic.product.controller.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * POJO Product Args represented by class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductArg {
    @ApiModelProperty(notes = "product sku", example = "skusku", required = true)
    @Size(min = 3, max = 10, message = "The product sku does not meet the text length requirements")
    @NotNull(message = "The product sku cannot be empty")
    private String sku;
    @ApiModelProperty(notes = "Product name", example = "Iphone 15", required = true)
    @Size(min = 3, max = 40, message = "The product name does not meet the text length requirements")
    @NotNull(message = "The product name cannot be empty")
    private String name;
    @ApiModelProperty(notes = "Product photo link", example = "link here", required = true)
    @NotNull(message = "The product pictureUrl cannot be empty")
    private String pictureUrl;
    @ApiModelProperty(notes = "Product description", example = "super cool phone can fly!",
            required = true)
    @Size(min = 10, message = "The product description does not meet the text length requirements")
    @NotNull(message = "The product description cannot be empty")
    private String description;
    @ApiModelProperty(notes = "product categories list", example = "See footnote of Categories",
            required = true)
    @Size(min = 1, message = "the product must be added to at least one category")
    @NotNull(message = "the product must be added to at least one category")
    private List<String> categoryNames;
    @ApiModelProperty(notes = "Product visible status", example = "true")
    @NotNull(message = "Product visibility status must be specified")
    private Boolean visible;
}
