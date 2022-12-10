package pl.opinion_collector.backend.logic.product.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Exception dto class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExceptionDto {
    @ApiModelProperty(notes = "Exception reason", example = "the specified page is outside of the page range", required = true)
    String message;
}
