package pl.opinion_collector.backend.logic.product.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.opinion_collector.backend.logic.product.controller.dto.Mapper;
import pl.opinion_collector.backend.logic.product.controller.dto.ProductExtendDto;
import pl.opinion_collector.backend.logic.product.controller.dto.ProductWrapperDto;
import pl.opinion_collector.backend.logic.product.controller.pojo.ProductArg;
import pl.opinion_collector.backend.logic.product.service.ProductFacade;
import pl.opinion_collector.backend.logic.user.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@CrossOrigin
@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Mapper map = new Mapper();
    @Autowired
    private ProductFacade productFacade;
    @Autowired
    private UserFacade userFacade;

    /**
     * Get visible products list
     *
     * @param page - number of page
     * @return {@link ProductWrapperDto}
     */
    @ApiParam(
            name = "page",
            type = "Integer",
            value = "page number",
            required = true)
    @GetMapping("/{page}")
    public ResponseEntity<ProductWrapperDto> getProduct(@PathVariable int page) {
        return new ResponseEntity<>(map.mapProductWrapper(productFacade.getProducts(page)),
                HttpStatus.OK);
    }

    /**
     * Get all products list
     *
     * @param page - number of page
     * @return {@link ProductWrapperDto}
     */
    @ApiParam(
            name = "page",
            type = "Integer",
            value = "page number",
            required = true)
    @GetMapping("/all/{page}")
    public ResponseEntity<ProductWrapperDto> getAllProduct(@PathVariable int page) {
        return new ResponseEntity<>(map.mapProductWrapper(productFacade.getAllProducts(page)),
                HttpStatus.OK);
    }

    /**
     * get filtered products
     *
     * @param categoryName category Name
     * @param searchPhrase searched key phrase
     * @param opinionAvgMin lower opinion range
     * @param opinionAvgMax upper range of opinions
     * @return {@link List<ProductWrapperDto>}
     */
    @ApiParam(
            name = "searchArg",
            type = "SearchArg",
            value = "contains search parameters",
            required = true)
    @GetMapping("/search")
    public ResponseEntity<List<ProductExtendDto>> searchProducts(@RequestParam(name = "categoryName",
                                                                   required = false) String categoryName,
                                                                 @RequestParam(name = "searchPhrase",
                                                                   required = false) String searchPhrase,
                                                                 @RequestParam(name = "opinionAvgMin",
                                                                   required = false) Integer opinionAvgMin,
                                                                 @RequestParam(name = "opinionAvgMax",
                                                                   required = false) Integer opinionAvgMax) {
        return new ResponseEntity<>(productFacade.getProductsFiltered(categoryName,
                        searchPhrase,
                        opinionAvgMin,
                        opinionAvgMax).stream()
                .map(map::mapProduct)
                .toList(), HttpStatus.OK);
    }

    /**
     * get Product details
     *
     * @param sku - product sku
     * @return {@link ProductExtendDto}
     */
    @ApiParam(
            name = "sku",
            type = "String",
            value = "product sku",
            required = true)
    @GetMapping("/details")
    public ResponseEntity<ProductExtendDto> getProductsDetails(@RequestParam(name = "sku") String sku) {
        return new ResponseEntity<>(map.mapProduct(productFacade.getProductBySku(sku)),
                HttpStatus.OK);
    }

    /**
     * Current user adds Product
     *
     * @param req - servlet container
     * @param arg - {@link ProductArg}
     * @return {@link ProductExtendDto}
     */
    @ApiParam(
            name = "arg",
            type = "ProductArg",
            value = "products params",
            required = true)
    @PostMapping("/add")
    public ResponseEntity<ProductExtendDto> addProduct(HttpServletRequest req,
                                                       @RequestBody @Valid ProductArg arg) {
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
     * @return {@link ProductExtendDto}
     */
    @ApiParam(
            name = "arg",
            type = "ProductArg",
            value = "products params",
            required = true)
    @PutMapping("/edit")
    public ResponseEntity<ProductExtendDto> editProduct(HttpServletRequest req,
                                                        @RequestBody @Valid ProductArg arg) {
        return new ResponseEntity<>(map.mapProduct(productFacade.editProduct(userFacade.
                        getUserByToken(getToken(req)).getUserId(),
                arg.getSku(),
                arg.getName(),
                arg.getPictureUrl(),
                arg.getDescription(),
                arg.getCategoryNames(),
                arg.getVisible())), HttpStatus.ACCEPTED);
    }

    /**
     * delete Product
     *
     * @param sku - sku of Product
     * @return {@link ProductExtendDto}
     */
    @ApiParam(
            name = "sku",
            type = "String",
            value = "Contains the sku of the Product",
            required = true)
    @DeleteMapping("/delete")
    public ResponseEntity<ProductExtendDto> deleteProduct(@RequestParam(name = "sku") String sku) {
        return new ResponseEntity<>(map.mapProduct(productFacade.removeProduct(sku)),
                HttpStatus.ACCEPTED);
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        return authorizationHeader.substring("Bearer ".length());
    }

}
