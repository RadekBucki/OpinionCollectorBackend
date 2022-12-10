package pl.opinion_collector.backend.logic.product.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.opinion_collector.backend.logic.product.controller.dto.Mapper;
import pl.opinion_collector.backend.logic.product.controller.dto.ProductDto;
import pl.opinion_collector.backend.logic.product.controller.pojo.ProductArg;
import pl.opinion_collector.backend.logic.product.controller.pojo.SearchArg;
import pl.opinion_collector.backend.logic.product.service.ProductFacade;
import pl.opinion_collector.backend.logic.user.UserFacade;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@CrossOrigin
@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Mapper map = new Mapper();
    @Autowired
    private ProductFacade productFacade;
    //    @Autowired
    private UserFacade userFacade;

    /**
     * Endpoint for all visible Product
     *
     * @return - list of all visible Product
     */
    @GetMapping()
    public ResponseEntity<?> getProduct(int page) {
//        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
//                .map(map::mapProduct), HttpStatus.OK);
        return null;
    }

    /**
     * Endpoint for all Product
     *
     * @return - list of all Product
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllProduct(int page) {
//        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
//                .map(map::mapProduct), HttpStatus.OK);
        return null;
    }

    /**
     * get filtered products
     * @param searchArg - {@link SearchArg}
     * @return
     */
    @ApiParam(
            name = "searchArg",
            type = "SearchArg",
            value = "contains search parameters",
            required = true)
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestBody SearchArg searchArg) {
        return new ResponseEntity<>(productFacade.getProductsFiltered(searchArg.getCategoryName(),
                        searchArg.getSearchPhrase(),
                        searchArg.getOpinionAvgMin(),
                        searchArg.getOpinionAvgMax()).stream()
                .map(map::mapProduct)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * get Product details
     *
     * @param sku - product sku
     */
    @ApiParam(
            name = "sku",
            type = "String",
            value = "product sku",
            required = true)
    @GetMapping("/details")
    public ResponseEntity<ProductDto> getProductsDetails(String sku) {
        return new ResponseEntity<>(map.mapProduct(productFacade.getProductBySku(sku)),
                HttpStatus.OK);
    }

    /**
     * Current user adds Product
     *
     * @param req - servlet container
     * @param arg - {@link ProductArg}
     */
    @ApiParam(
            name = "arg",
            type = "ProductArg",
            value = "products params",
            required = true)
    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(HttpServletRequest req,
                                                 @RequestBody ProductArg arg) {
        return new ResponseEntity<>(map.mapProduct(productFacade.addProduct(userFacade.
                        getUserByToken(getToken(req)).getUserId(),
                arg.getSku(),
                arg.getName(),
                arg.getPictureUrl(),
                arg.getDescription(),
                arg.getCategoryNames(),
                arg.getVisible())), HttpStatus.OK);
    }

    /**
     * edit Product data
     *
     * @param req - servlet container
     * @param arg - {@link ProductArg}
     */
    @ApiParam(
            name = "arg",
            type = "ProductArg",
            value = "products params",
            required = true)
    @PutMapping("/edit")
    public ResponseEntity<?> editProduct(HttpServletRequest req, @RequestBody ProductArg arg) {
        productFacade.editProduct(userFacade.
                        getUserByToken(getToken(req)).getUserId(),
                arg.getSku(),
                arg.getName(),
                arg.getPictureUrl(),
                arg.getDescription(),
                arg.getCategoryNames(),
                arg.getVisible());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * delete Product
     *
     * @param sku - sku of Product
     */
    @ApiParam(
            name = "sku",
            type = "String",
            value = "Contains the sku of the Product",
            required = true)
    @DeleteMapping("/edit")
    public ResponseEntity<?> deleteProduct(@RequestBody String sku) {
        productFacade.removeProduct(sku);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        return authorizationHeader.substring("Bearer ".length());
    }

}
