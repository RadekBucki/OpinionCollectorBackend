package pl.opinion_collector.backend.logic.opinion;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.logic.opinion.dto.OpinionDto;
import pl.opinion_collector.backend.logic.user.UserFacade;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@CrossOrigin
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
    public ResponseEntity<List<OpinionDto>> getUserOpinions(HttpServletRequest req) {
        User user = userFacade.getUserByToken(getBearerTokenHeader(req));
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
    public ResponseEntity<OpinionDto> addOpinion(HttpServletRequest req, @RequestBody OpinionDto opinionDto) {

        User user = userFacade.getUserByToken(getBearerTokenHeader(req));

        return ResponseEntity.ok().body(opinionsFacade.addProductOpinion(user.getUserId(), opinionDto.getSku(),
                opinionDto.getOpinionValue(), opinionDto.getDescription(), opinionDto.getPictureUrl(),
                opinionDto.getAdvantages(), opinionDto.getDisadvantages()));
    }

    public static String getBearerTokenHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        return authorizationHeader.substring("Bearer ".length());
    }
}
