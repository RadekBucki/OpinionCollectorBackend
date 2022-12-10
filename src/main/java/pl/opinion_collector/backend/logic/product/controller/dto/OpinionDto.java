package pl.opinion_collector.backend.logic.product.controller.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OpinionDto {
    private Integer opinionValue;
    private String description;
    private String pictureUrl;
    private List<String> advantages;
    private List<String> disadvantages;
    private String firstName;
}
