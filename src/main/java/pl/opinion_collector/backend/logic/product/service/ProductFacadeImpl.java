package pl.opinion_collector.backend.logic.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.logic.product.service.wrapper.ProductWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductFacadeImpl implements ProductFacade {

    private static final int PRODUCT_COUNT = 20;

    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public Product getProductBySku(String sku) {
        return null;
    }

    @Override
    public List<Product> getAllProducts(String page) {
        //TODO: wrap Product list with ProductWrapper.builder().build();
        return null;
    }

    @Override
    public List<Product> getProducts(String page) {
        //TODO: wrap Product list with ProductWrapper.builder().build();
        return null;
    }

    @Override
    public List<Product> getProductsFiltered(String categoryName, String searchPhrase, Integer opinionAvgMin, Integer opinionAvgMax) {
        Set<Product> productSet = new HashSet<>();
        return null;
    }

    @Override
    public List<Product> addProduct(String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        return null;
    }

    @Override
    public Product editProduct(String sku, String name, String pictureUrl, String description, List<String> categoryNames, Boolean visible) {
        return null;
    }

    @Override
    public void removeProduct(String sku) {

    }

    @Override
    public Category addCategory(String categoryName, Boolean visible) {
        return null;
    }

    @Override
    public Category editCategory(String categoryName, Boolean visible) {
        return null;
    }

    @Override
    public void removeCategory(String categoryName) {

    }

}
