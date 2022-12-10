package pl.opinion_collector.backend.logic.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.logic.product.service.wrapper.ProductWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductFacadeImpl implements ProductFacade {

    private static final int PRODUCT_COUNT = 20;

    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public Product getProductBySku(String sku) {
        return databaseCommunication.getProductBySku(sku);
    }

    @Override
    public List<Product> getAllProducts(String page) {
        return databaseCommunication.getAllProducts();
    }

    @Override
    public ProductWrapper getProducts(String page) {
        List<Product> allProducts = databaseCommunication.getVisibleProducts();
        int size = (int) Math.ceil(allProducts.size() / PRODUCT_COUNT);
        List<Product> productList = new ArrayList<>(allProducts
                .subList(Integer.parseInt(page)* PRODUCT_COUNT,
                        Integer.parseInt(page)* PRODUCT_COUNT + PRODUCT_COUNT));
        return ProductWrapper.builder()
                .numberOfPages(String.valueOf(size))
                .actualPage(page)
                .products(productList).build();
    }

    @Override
    public List<Product> getProductsFiltered(String categoryName,
                                             String searchPhrase,
                                             Integer opinionAvgMin,
                                             Integer opinionAvgMax) {
        return databaseCommunication.getProductsFilterProducts(categoryName,
                searchPhrase,
                opinionAvgMin,
                opinionAvgMax);
    }

    @Override
    public Product addProduct(Long authorId,
                              String sku,
                              String name,
                              String pictureUrl,
                              String description,
                              List<String> categoryNames,
                              Boolean visible) {
        return databaseCommunication.createProduct(authorId,
                sku,
                name,
                pictureUrl,
                description,
                categoryNames,
                visible);
    }

    @Override
    public void editProduct(Long authorId,
                               String sku,
                               String name,
                               String pictureUrl,
                               String description,
                               List<String> categoryNames,
                               Boolean visible) {
        databaseCommunication.updateProduct(authorId,
                sku,
                name,
                pictureUrl,
                description,
                categoryNames,
                visible);
    }

    @Override
    public void removeProduct(String sku) {
        databaseCommunication.removeProduct(sku);
    }

    @Override
    public Category addCategory(String categoryName, Boolean visible) {
        return databaseCommunication.createCategory(categoryName, visible);
    }

    @Override
    public void editCategory(String categoryName, Boolean visible) {
        databaseCommunication.updateCategory(categoryName, visible);
    }

    @Override
    public void removeCategory(String categoryName) {
        databaseCommunication.removeCategory(categoryName);
    }

    @Override
    public List<Category> getCategories() {
        return databaseCommunication.getAllCategories()
                .stream()
                .filter(Category::getVisible)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> getAllCategories() {
        return databaseCommunication.getAllCategories();
    }

    private List<Category> getCategories(List<String> names) {
        return databaseCommunication.getAllCategories()
                .stream().filter(category -> names.stream()
                        .anyMatch(e -> category.getCategoryName().equals(e)))
                .collect(Collectors.toList());
    }

    private Page<?> toPage(List<?> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        if(start > list.size())
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

}
