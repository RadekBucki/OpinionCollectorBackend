package pl.opinion_collector.backend.logic.product.controller.dto;

import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.Product;

import java.util.stream.Collectors;

/**
 * Helper class used to avoid dumping huge JSON onto frontend
 */
public final class Mapper {

    public ProductDto mapProduct(Product product) {
        return new ProductDto.ProductDtoBuilder()
                .sku(product.getSku())
                .name(product.getName())
                .pictureUrl(product.getPictureUrl())
                .description(product.getDescription())
                .opinionAvg(product.getOpinionAvg())
                .firstName(product.getAuthorId().getFirstName())
                .opinions(product.getOpinions().stream().map(this::mapOpinion).collect(Collectors.toList()))
                .categories(product.getCategories().stream().map(this::mapCategory).collect(Collectors.toList()))
                .build();
    }

    public OpinionDto mapOpinion(Opinion opinion) {
        return new OpinionDto.OpinionDtoBuilder()
                .opinionValue(opinion.getOpinionValue())
                .description(opinion.getDescription())
                .pictureUrl(opinion.getPictureUrl())
                .advantages(opinion.getAdvantages())
                .disadvantages(opinion.getDisadvantages())
                .firstName(opinion.getUserId().getFirstName())
                .build();
    }

    public CategoryDto mapCategory(Category category) {
        return new CategoryDto.CategoryDtoBuilder()
                .categoryName(category.getCategoryName())
                .build();
    }
}
