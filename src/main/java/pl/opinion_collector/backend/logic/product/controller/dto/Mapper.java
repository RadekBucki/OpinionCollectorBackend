package pl.opinion_collector.backend.logic.product.controller.dto;

import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.logic.product.service.wrapper.ProductWrapper;

/**
 * a class that maps objects from the database layer to the communication layer
 */
public final class Mapper {

    /**
     * get converted Product class
     *
     * @param product - Product from dataBase layer
     * @return ProductDto
     */
    public ProductDto mapProduct(Product product) {
        return new ProductDto.ProductDtoBuilder()
                .sku(product.getSku())
                .name(product.getName())
                .pictureUrl(product.getPictureUrl())
                .description(product.getDescription())
                .opinionAvg(product.getOpinionAvg())
                .firstName(product.getAuthorId().getFirstName())
                .opinions(product.getOpinions().stream().map(this::mapOpinion).toList())
                .categories(product.getCategories().stream().map(this::mapCategory).toList())
                .build();
    }

    /**
     * get converted Opinion class
     *
     * @param opinion - Opinion from dataBase layer
     * @return OpinionDto
     */
    public OpinionProductDto mapOpinion(Opinion opinion) {
        return new OpinionProductDto.OpinionProductDtoBuilder()
                .opinionValue(opinion.getOpinionValue())
                .description(opinion.getDescription())
                .pictureUrl(opinion.getPictureUrl())
                .advantages(opinion.getAdvantages())
                .disadvantages(opinion.getDisadvantages())
                .firstName(opinion.getUserId().getFirstName())
                .build();
    }

    /**
     * get converted Category class
     *
     * @param category - Category from dataBase layer
     * @return CategoryDto
     */
    public CategoryDto mapCategory(Category category) {
        return new CategoryDto.CategoryDtoBuilder()
                .categoryName(category.getCategoryName())
                .visible(category.getVisible())
                .build();
    }

    /**
     * get converted ProductWrapper class
     *
     * @param productWrapper - ProductWrapper from Service layer
     * @return ProductWrapperDto
     */
    public ProductWrapperDto mapProductWrapper(ProductWrapper productWrapper) {
        return ProductWrapperDto.builder()
                .actualPage(productWrapper.getActualPage())
                .numberOfPages(productWrapper.getNumberOfPages())
                .products(productWrapper.getProducts().stream()
                        .map(this::mapProduct)
                        .toList())
                .build();
    }

    /**
     * get wrapped exception reason
     *
     * @param e - Exception
     * @return ExceptionDto
     */
    public ExceptionDto mapException(Exception e){
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }

}
