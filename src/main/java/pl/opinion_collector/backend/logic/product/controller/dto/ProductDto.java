package pl.opinion_collector.backend.logic.product.controller.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private String sku;
    private String name;
    private String pictureUrl;
    private String description;
    private Double opinionAvg;
    private String firstName;
    private List<OpinionDto> opinions = new ArrayList<>();
    private List<CategoryDto> categories;
}
