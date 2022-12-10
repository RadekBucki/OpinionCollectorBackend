package pl.opinion_collector.backend.logic.opinion;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.logic.user.UserFacade;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/opinions")
public class OpinionController {

    @Autowired
    private Opinions opinionsFacade;
    //    @Autowired
    private UserFacade userFacade;

    /**
     * All opinions endpoint
     *
     * @return list of all opinions of user
     */
    @GetMapping("/user")
    public ResponseEntity<List<OpinionShortDto>> getUserOpinions() {
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<Opinion> userOpinions = opinionsFacade.getUserOpinions(user.getUserId());
        List<OpinionShortDto> collect = userOpinions.stream().
                map(this::mapOpinionToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    /**
     * Endpoint for all opinions of given products
     *
     * @param sku - identifier of a product
     * @return - list of all opinions of given products
     */
    @ApiParam(
            name = "sku",
            type = "String",
            value = "Product identifier (sku)",
            example = "sku123",
            required = true)
    @GetMapping("/product")
    public ResponseEntity<List<OpinionShortDto>> getProductOpinions(@RequestParam String sku) {
        List<Opinion> productOpinions = opinionsFacade.getProductOpinions(sku);
        List<OpinionShortDto> collect = productOpinions.stream().
                map(this::mapOpinionToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    /**
     * Endpoint for Logged-in user to add his opinion.
     *
     * @param shortOpinionDto - opinion data transfer object
     */
    @ApiParam(
            name = "shortOpinionDto",
            type = "OpinionShortDto",
            value = "Vital information about Opinion: sku (product identifier), opinion value, description " +
                    "(content of opinion), picture URL, list of advantages, list of disadvantages",
            required = true)
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Opinion> addOpinion(@RequestBody OpinionShortDto shortOpinionDto) {

        // check whether user is valid
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        // add opinion
        try {
            Opinion opinion = opinionsFacade.addProductOpinion(user.getUserId(), shortOpinionDto.sku, shortOpinionDto.opinionValue,
                    shortOpinionDto.description, shortOpinionDto.pictureUrl, shortOpinionDto.advantages,
                    shortOpinionDto.disadvantages);
            return ResponseEntity.ok().body(opinion);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    /**
     * Helper class used to avoid dumping huge JSON onto frontend
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static class OpinionShortDto {
        @ApiModelProperty(notes = "Product identifier", example = "sku123", required = true)
        private String sku;
        @ApiModelProperty(notes = "Opinion value, grade", example = "1", required = true)
        private Integer opinionValue;
        @ApiModelProperty(notes = "Content of opinion", example = "dont like it at all", required = true)
        private String description;
        @ApiModelProperty(notes = "URL of opinion picture", example = "1")
        private String pictureUrl;
        @ApiModelProperty(notes = "List of advantages")
        private List<String> advantages;
        @ApiModelProperty(notes = "List of disadvantages")
        private List<String> disadvantages;

    }

    /**
     * Helper functions
     */
    private OpinionShortDto mapOpinionToDto(Opinion opinion) {
        return new OpinionShortDto(opinion.getProductId().getSku(),
                opinion.getOpinionValue(), opinion.getDescription(),
                opinion.getPictureUrl(), opinion.getAdvantages(), opinion.getDisadvantages());
    }

    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
    }

}
