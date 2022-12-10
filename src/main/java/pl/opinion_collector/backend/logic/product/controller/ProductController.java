package pl.opinion_collector.backend.logic.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.opinion_collector.backend.logic.product.controller.dto.Mapper;
import pl.opinion_collector.backend.logic.product.service.ProductFacade;

@CrossOrigin
@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Mapper map = new Mapper();

    @Autowired
    private ProductFacade productFacade;

    //example
    @GetMapping("/test")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>(productFacade.getAllProducts("1").stream()
                .map(map::mapProduct), HttpStatus.OK);
    }

}
