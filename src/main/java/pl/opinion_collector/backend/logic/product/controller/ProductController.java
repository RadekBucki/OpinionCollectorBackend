package pl.opinion_collector.backend.logic.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.opinion_collector.backend.logic.product.controller.dto.Mapper;
import pl.opinion_collector.backend.logic.product.service.ProductFacade;

@CrossOrigin
@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Mapper map = new Mapper();
    @Autowired
    private ProductFacade productFacade;

    @GetMapping()
    public ResponseEntity<Object> getProduct() {
//        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
//                .map(map::mapProduct), HttpStatus.OK);
        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllProduct() {
//        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
//                .map(map::mapProduct), HttpStatus.OK);
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchProducts() {
//        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
//                .map(map::mapProduct), HttpStatus.OK);
        return null;
    }

    @GetMapping("/details")
    public ResponseEntity<Object> getProductsDetails() {
//        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
//                .map(map::mapProduct), HttpStatus.OK);
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addProduct() {
//        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
//                .map(map::mapProduct), HttpStatus.OK);
        return null;
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editProduct() {
//        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
//                .map(map::mapProduct), HttpStatus.OK);
        return null;
    }

    @DeleteMapping("/edit")
    public ResponseEntity<Object> deleteProduct() {
//        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
//                .map(map::mapProduct), HttpStatus.OK);
        return null;
    }

}
