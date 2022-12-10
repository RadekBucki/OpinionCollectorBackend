package pl.opinion_collector.backend.logic.opinion;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.opinion_collector.backend.logic.opinion.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.logic.opinion.dto.OpinionShortDto;
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

        List<OpinionShortDto> collect = opinionsFacade.getUserOpinions(user.getUserId()).stream()
                .map(OpinionShortDto::map).collect(Collectors.toList());
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
        List<OpinionShortDto> collected = opinionsFacade.getProductOpinions(sku).stream()
                .map(OpinionShortDto::map).collect(Collectors.toList());
        return ResponseEntity.ok().body(collected);
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
            return ResponseEntity.ok().body(opinionsFacade.addProductOpinion(user.getUserId(), shortOpinionDto.getSku(),
                    shortOpinionDto.getOpinionValue(), shortOpinionDto.getDescription(), shortOpinionDto.getPictureUrl(),
                    shortOpinionDto.getAdvantages(), shortOpinionDto.getDisadvantages()));
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
