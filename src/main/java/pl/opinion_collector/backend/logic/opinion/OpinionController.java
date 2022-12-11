package pl.opinion_collector.backend.logic.opinion;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.logic.opinion.dto.OpinionDto;
import pl.opinion_collector.backend.logic.user.UserFacade;

import java.util.List;

@RestController
@RequestMapping("/opinions")
public class OpinionController {

    @Autowired
    private Opinions opinionsFacade;
    @Autowired
    private UserFacade userFacade;

    /**
     * All opinions endpoint
     *
     * @return list of all opinions of user
     */
    @GetMapping("/user")
    public ResponseEntity<List<OpinionDto>> getUserOpinions() {
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        return ResponseEntity.ok().body(opinionsFacade.getUserOpinions(user.getUserId()));
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
    public ResponseEntity<List<OpinionDto>> getProductOpinions(@RequestParam String sku) {
        return ResponseEntity.ok().body(opinionsFacade.getProductOpinions(sku));
    }

    /**
     * Endpoint for Logged-in user to add his opinion.
     *
     * @param opinionDto - opinion data transfer object
     */
    @ApiParam(
            name = "opinionDto",
            type = "OpinionDto",
            value = "Vital information about Opinion: opinionId (id of opinion), sku (product identifier), opinion value," +
                    " description (content of opinion), picture URL, list of advantages, list of disadvantages, " +
                    "authorId, productId",
            required = true)
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OpinionDto> addOpinion(@RequestBody OpinionDto opinionDto) {

        User user = userFacade.getUserByToken(getBearerTokenHeader());

        // add opinion
        try {
            return ResponseEntity.ok().body(opinionsFacade.addProductOpinion(user.getUserId(), opinionDto.getSku(),
                    opinionDto.getOpinionValue(), opinionDto.getDescription(), opinionDto.getPictureUrl(),
                    opinionDto.getAdvantages(), opinionDto.getDisadvantages()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }


    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
    }

}
