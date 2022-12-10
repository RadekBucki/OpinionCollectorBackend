package pl.opinion_collector.backend.logic.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.opinion_collector.backend.logic.product.controller.dto.CategoryDto;
import pl.opinion_collector.backend.logic.product.controller.dto.Mapper;
import pl.opinion_collector.backend.logic.product.service.ProductFacade;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private static final Mapper map = new Mapper();
    @Autowired
    private ProductFacade productFacade;

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return new ResponseEntity<>(productFacade.getCategories().stream()
                .map(map::mapCategory)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllCategories() {
        return new ResponseEntity<>(productFacade.getAllCategories().stream()
                .map(map::mapCategory)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addCategory(@RequestBody String name,
                                              @RequestBody Boolean visible) {
        return new ResponseEntity<>(map.mapCategory(productFacade.addCategory(name, visible)),
                HttpStatus.ACCEPTED);
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editCategory(@RequestBody String name,
                                               @RequestBody Boolean visible) {
        return new ResponseEntity<>(map.mapCategory(productFacade.editCategory(name, visible)),
                HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(@RequestBody String name) {
        productFacade.removeCategory(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
