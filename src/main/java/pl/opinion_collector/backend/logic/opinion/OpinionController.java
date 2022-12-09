package pl.opinion_collector.backend.logic.opinion;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/opinions")
public class OpinionController {

    @Autowired
    private Opinions opinionsFacade;
    private UserFacade userFacade;
    private ProductFacade productFacade;

    /**
     * All opinions endpoint
     *
     * @return list of all opinions of user
     */
    @GetMapping("/user")
    public List<OpinionShortDto> getUserOpinions() {
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            throw new IllegalArgumentException("Authentication failed!");
        }
        List<Opinion> userOpinions = opinionsFacade.getUserOpinions(user.getUserId());
        return userOpinions.stream().map(this::mapOpinionToDto).collect(Collectors.toList());
    }

    /**
     * Endpoint for all opinions of given products
     *
     * @param sku - identifier of a product
     * @return - list of all opinions of given products
     */
    @GetMapping("/product")
    public List<OpinionShortDto> getProductOpinions(@RequestParam String sku) {
        List<Opinion> productOpinions = opinionsFacade.getProductOpinions(sku);
        return productOpinions.stream().map(this::mapOpinionToDto).collect(Collectors.toList());
    }

    /**
     * Endpoint for Logged-in user to add his opinion.
     *
     * @param shortOpinionDto - opinion data transfer object
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addOpinion(@RequestBody OpinionShortDto shortOpinionDto) {

        // check whether user is valid
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            throw new IllegalArgumentException("Authentication failed!");
        }
        // check whether product is valid
        Product product = productFacade.getProductBySku(shortOpinionDto.sku);
        if (product == null) {
            throw new IllegalArgumentException("Product with given SKU does not exist");
        }

        // add opinion
        opinionsFacade.addProductOpinion(user.getUserId(), shortOpinionDto.sku, shortOpinionDto.opinionValue,
                shortOpinionDto.description, shortOpinionDto.pictureUrl, shortOpinionDto.advantages,
                shortOpinionDto.disadvantages);
    }

    /**
     * Helper class used to avoid dumping huge JSON onto frontend
     */
    @Data
    @AllArgsConstructor
    private class OpinionShortDto {
        private Long authorId;
        private String sku;
        private Integer opinionValue;
        private String description;
        private String pictureUrl;
        private List<String> advantages;
        private List<String> disadvantages;

    }

    /**
     * Helper functions
     */
    private OpinionShortDto mapOpinionToDto(Opinion opinion) {
        return new OpinionShortDto(opinion.getUserId().getUserId(), opinion.getProductId().getSku(),
                opinion.getOpinionValue(), opinion.getDescription(),
                opinion.getPictureUrl(), opinion.getAdvantages(), opinion.getDisadvantages());
    }

    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
    }

}
