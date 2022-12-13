package pl.opinion_collector.backend.logic.product.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.opinion_collector.backend.logic.product.controller.dto.CategoryDto;
import pl.opinion_collector.backend.logic.product.controller.dto.Mapper;
import pl.opinion_collector.backend.logic.product.controller.pojo.CategoryArg;
import pl.opinion_collector.backend.logic.product.service.ProductFacade;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private static final Mapper map = new Mapper();
    @Autowired
    private ProductFacade productFacade;

    /**
     * Endpoint for all visible Categories
     *
     * @return {@link List<CategoryDto>}
     */
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return new ResponseEntity<>(productFacade.getCategories().stream()
                .map(map::mapCategory)
                .toList(),
                HttpStatus.OK);
    }

    /**
     * Endpoint for all Categories
     *
     * @return {@link List<CategoryDto>}
     */
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(productFacade.getAllCategories().stream()
                .map(map::mapCategory)
                .toList(),
                HttpStatus.OK);
    }

    /**
     * adds category
     *
     * @param categoryArg - {@link CategoryArg}
     * @return {@link CategoryDto}
     */
    @ApiParam(
            name = "categoryArg",
            type = "CategoryArg",
            value = "Contains the name and visibility status of the added category",
            required = true)
    @PostMapping("/add")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid CategoryArg categoryArg) {
        return new ResponseEntity<>(map.mapCategory(productFacade.addCategory(categoryArg.getName(),
                categoryArg.getVisible())),
                HttpStatus.ACCEPTED);
    }

    /**
     * edit category visibility
     *
     * @param categoryArg - {@link CategoryArg}
     * @return {@link CategoryDto}
     */
    @ApiParam(
            name = "categoryArg",
            type = "CategoryArg",
            value = "Contains the name and visibility status of the added category",
            required = true)
    @PutMapping("/edit")
    public ResponseEntity<CategoryDto> editCategory(@RequestBody @Valid CategoryArg categoryArg) {
        return new ResponseEntity<>(map.mapCategory(productFacade.editCategory(categoryArg.getName(),
                categoryArg.getVisible())), HttpStatus.ACCEPTED);
    }

    /**
     * delete Category
     *
     * @param name - name of Category
     * @return {@link CategoryDto}
     */
    @ApiParam(
            name = "name",
            type = "String",
            value = "Contains the name of the category",
            required = true)
    @DeleteMapping("/delete")
    public ResponseEntity<CategoryDto> deleteCategory(@RequestParam("name") String name) {
        return new ResponseEntity<>(map.mapCategory(productFacade.removeCategory(name)),
                HttpStatus.ACCEPTED);
    }

}
