package pl.opinion_collector.backend.logic.suggestion;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.opinion_collector.backend.database_communication.model.*;
import pl.opinion_collector.backend.logic.user.UserFacade;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/suggestions")
public class SuggestionController {

    @Autowired
    private Suggestions suggestionFacade;
    // @Autowired
    private UserFacade userFacade;

    /**
     * All Suggestions endpoint
     *
     * @return list of all Suggestions of user
     */
    @GetMapping("/user")
    public ResponseEntity<List<SuggestionShortDto>> getUserSuggestions() {
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
        List<Suggestion> userSuggestions = suggestionFacade.getUserSuggestions(user.getUserId());
        List<SuggestionShortDto> collect = userSuggestions.stream().
                map(this::mapSuggestionToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    /**
     * Endpoint for all Suggestions
     *
     * @return - list of all Suggestions
     */
    @GetMapping("/get")
    public ResponseEntity<List<SuggestionShortDto>> getAllSuggestions() {
        List<SuggestionShortDto> collect = suggestionFacade.getAllSuggestions().stream().
                map(this::mapSuggestionToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    /**
     * Current user adds suggestion
     *
     * @param argHolder - sku and description
     */
    @ApiParam(
            name = "argHolder",
            type = "ArgHolder",
            value = "Contains crucial info about suggestion to be added: sku (product identifier), " +
                    "description (content of suggestion)",
            required = true)
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Suggestion> addSuggestion(@RequestBody ArgHolder argHolder) {

        String sku = argHolder.getSku();
        String description = argHolder.getDescription();

        // check whether user is valid
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        // add suggestion
        Suggestion suggestion = suggestionFacade.addSuggestion(user.getUserId(), sku, description);
        return ResponseEntity.ok().body(suggestion);
    }

    @ApiParam(
            name = "argHolderTwo",
            type = "ArgHolderTwo",
            value = "Contains crucial reply to suggestion: suggestionId (what suggestion is being answered), " +
                    "suggestionStatus (what status should suggestion have (DECLINED / PENDING / DONE))" +
                    "suggestionReply (content of reply)",
            required = true)
    @PutMapping(value = "/reply", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> replyToSuggestion(@RequestBody ArgHolderTwo argHolderTwo) {

        Integer suggestionId = argHolderTwo.getSuggestionId();
        String suggestionStatus = argHolderTwo.getSuggestionStatus().name();
        String suggestionReply = argHolderTwo.getSuggestionReply();

        // check whether user is valid
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User authentication failed!");
        }
        if (!user.getAdmin()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Only admin can reply to suggestions!");
        }

        suggestionFacade.replySuggestion(user.getUserId(), suggestionId, suggestionStatus, suggestionReply);

        return ResponseEntity.ok().body("Successfully replied to suggestion");
    }

    /**
     * Helper classes used to avoid dumping huge JSON onto frontend
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static class SuggestionShortDto {
        @ApiModelProperty(notes = "ID of suggestion", example = "1", required = true)
        private Long suggestionId;
        @ApiModelProperty(notes = "Suggestion reviewer", example = "5", required = true)
        private Review reviewId;
        @ApiModelProperty(notes = "ID of user that created suggestion", example = "2", required = true)
        private Long userId;
        @ApiModelProperty(notes = "Content of suggestion", example = "change the color of the toy!",
                required = true)
        private String description;
        @ApiModelProperty(notes = "ID of product addressed by suggestion", example = "1", required = true)
        private Long productId;
        @ApiModelProperty(notes = "ID of suggestion reviewer", example = "1", required = true)
        private Long reviewerId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static class ArgHolder {
        @ApiModelProperty(notes = "Content of suggestion", example = "Change the colors!", required = true)
        private String description;
        @ApiModelProperty(notes = "Product identifier", example = "1", required = true)
        private String sku;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static class ArgHolderTwo {
        @ApiModelProperty(notes = "ID of answered suggestion", example = "1", required = true)
        Integer suggestionId;
        @ApiModelProperty(notes = "New Status for suggestion", example = "DECLINED", required = true)
        SuggestionStatus suggestionStatus;
        @ApiModelProperty(notes = "Content of suggestion reply", example = "Thx for your suggestion", required = true)
        String suggestionReply;
    }


    /**
     * Helper functions
     */
    private SuggestionShortDto mapSuggestionToDto(Suggestion suggestion) {
        return new SuggestionShortDto(suggestion.getSuggestionId(), suggestion.getReview(),
                suggestion.getUserId().getUserId(), suggestion.getDescription(),
                suggestion.getProductId().getProductId(), suggestion.getReviewerId().getUserId());
    }


    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
    }

    public enum SuggestionStatus {
        DECLINED, PENDING, DONE
    }
}
