package pl.opinion_collector.backend.logic.suggestion.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddSuggestionDto {
    @ApiModelProperty(notes = "Content of suggestion", example = "Change the colors!", required = true)
    private String description;
    @ApiModelProperty(notes = "Product identifier", example = "sku123", required = true)
    private String sku;
}