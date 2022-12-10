package pl.opinion_collector.backend.logic.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.opinion_collector.backend.logic.product.service.ProductFacade;

@CrossOrigin
@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ProductFacade productFacade;
}
