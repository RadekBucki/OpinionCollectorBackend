package pl.opinion_collector.backend.logic.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.logic.exception.type.DuplicatedDataException;
import pl.opinion_collector.backend.logic.exception.type.InvalidDataIdException;
import pl.opinion_collector.backend.logic.exception.type.ParameterException;
import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.logic.product.service.wrapper.ProductWrapper;

import java.util.List;
import java.util.Optional;

@Component
public class ProductFacadeImpl implements ProductFacade {

    private static final String INVALID_SKU = "The product with the given sku is not in the system";
    private static final String INVALID_NAME = "The Category with the given name is not in the system";
    @Value("${listSize.productListSize}")
    private int productCount;
    @Autowired
    private DatabaseCommunicationFacade databaseCommunication;

    @Override
    public Product getProductBySku(String sku) {
        return Optional.ofNullable(databaseCommunication.getProductBySku(sku))
                .orElseThrow(() -> new InvalidDataIdException(INVALID_SKU));
    }

    @Override
    public ProductWrapper getAllProducts(int page) {
        return wrapProductList(page, databaseCommunication.getAllProducts());
    }

    @Override
    public ProductWrapper getProducts(int page) {
        return wrapProductList(page, databaseCommunication.getVisibleProducts());
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
        Optional.ofNullable(databaseCommunication.getProductBySku(sku)).ifPresent(product -> {
            throw new DuplicatedDataException("The product with the given sku " +
                    product.getSku()+
                    " already exists");
        });
        return databaseCommunication.createProduct(authorId,
                sku,
                name,
                pictureUrl,
                description,
                categoryNames,
                visible);
    }

    @Override
    public Product editProduct(Long authorId,
                               String sku,
                               String name,
                               String pictureUrl,
                               String description,
                               List<String> categoryNames,
                               Boolean visible) {
        Product product = Optional.ofNullable(databaseCommunication.getProductBySku(sku))
                .orElseThrow(() -> new InvalidDataIdException(INVALID_SKU));
        databaseCommunication.updateProduct(authorId,
                sku,
                name,
                pictureUrl,
                description,
                categoryNames,
                visible);
        return product;
    }

    @Override
    public Product removeProduct(String sku) {
        Product product = Optional.ofNullable(databaseCommunication.getProductBySku(sku))
                .orElseThrow(() -> new InvalidDataIdException(INVALID_SKU));
        databaseCommunication.removeProduct(sku);
        return product;
    }

    @Override
    public Category addCategory(String categoryName, Boolean visible) {
        Optional.ofNullable(databaseCommunication.getCategoryByName(categoryName)).ifPresent(category -> {
            throw new DuplicatedDataException("The category with the given name " +
                    category.getCategoryName() +
                    " already exists");
        });
        return databaseCommunication.createCategory(categoryName, visible);
    }

    @Override
    public Category editCategory(String categoryName, Boolean visible) {
        Category category = Optional.ofNullable(databaseCommunication.getCategoryByName(categoryName))
                .orElseThrow(() -> new InvalidDataIdException(INVALID_NAME));
        databaseCommunication.updateCategory(categoryName, visible);
        return category;
    }

    @Override
    public Category removeCategory(String categoryName) {
        Category category = Optional.ofNullable(databaseCommunication.getCategoryByName(categoryName))
                .orElseThrow(() -> new InvalidDataIdException(INVALID_NAME));
        databaseCommunication.removeCategory(categoryName);
        return category;
    }

    @Override
    public List<Category> getCategories() {
        return databaseCommunication.getAllCategories()
                .stream()
                .filter(Category::getVisible)
                .toList();
    }

    @Override
    public List<Category> getAllCategories() {
        return databaseCommunication.getAllCategories();
    }

    private ProductWrapper wrapProductList(int page, List<Product> productList) {
        PagedListHolder<Product> paged = new PagedListHolder<>(productList);
        paged.setPageSize(productCount);
        paged.setPage(page - 1);
        if(page > paged.getPageCount() || page == 0) {
            throw new ParameterException("the specified page is outside of the page range");
        }

        return ProductWrapper.builder()
                .numberOfPages(paged.getPageCount())
                .products(paged.getPageList())
                .actualPage(page)
                .build();
    }

}
