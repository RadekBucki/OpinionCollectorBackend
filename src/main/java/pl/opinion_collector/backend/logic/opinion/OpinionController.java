package pl.opinion_collector.backend.logic.opinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.logic.product.ProductFacade;
import pl.opinion_collector.backend.logic.user.UserFacade;
import java.util.List;

@Controller
@RequestMapping("/opinions")
public class OpinionController {

    @Autowired
    private Opinions opinionsFacade;
    private UserFacade userFacade;
    private ProductFacade productFacade;

    /**
     * All opinions endpoint
     * @return list of all opinions of user
     */
    @GetMapping("/user")
    public List<Opinion> getUserOpinions() {
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            throw new IllegalArgumentException("Authentication failed!");
        }
        return opinionsFacade.getUserOpinions(user.getUserId());
    }

    /**
     * Endpoint for all opinions of given products
     * @param sku - identifier of a product
     * @return - list of all opinions of given products
     */
    @GetMapping("/product")
    public List<Opinion> getProductOpinions(@RequestParam String sku) {
        return opinionsFacade.getProductOpinions(sku);
    }

    /**
     * Endpoint for Logged-in user to add his opinion.
     * @param opinionDto - opinion data transfer object
     */
    @PostMapping("/add")
    public void addOpinion(@RequestBody OpinionShortDto opinionDto) {

        // check whether user is valid
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            throw new IllegalArgumentException("Authentication failed!");
        }
        // check whether product is valid
        Product product = productFacade.getProductBySku(opinionDto.sku);
        if (product == null) {
            throw new IllegalArgumentException("Product with given SKU does not exist");
        }
        // add opinion
        opinionsFacade.addProductOpinion(user.getUserId(), opinionDto.sku, opinionDto.opinionValue,
                opinionDto.description, opinionDto.pictureUrl, opinionDto.advantages, opinionDto.disadvantages);
    }

    private class OpinionShortDto {
        private String sku;
        private Integer opinionValue;
        private String description;
        private String pictureUrl;
        private List<String> advantages;
        private List<String> disadvantages;
    }

    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
    }

 }
