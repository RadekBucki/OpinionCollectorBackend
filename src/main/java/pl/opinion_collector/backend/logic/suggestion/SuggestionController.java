package pl.opinion_collector.backend.logic.suggestion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.opinion_collector.backend.database_communication.model.*;
import pl.opinion_collector.backend.logic.product.ProductFacade;
import pl.opinion_collector.backend.logic.user.UserFacade;

import java.util.List;

@Controller
@RequestMapping("/suggestions")
public class SuggestionController {

    @Autowired
    private Suggestions suggestionFacade;
    private UserFacade userFacade;
    private ProductFacade productFacade;

    /**
     * All Suggestions endpoint
     *
     * @return list of all Suggestions of user
     */
    @GetMapping("/user")
    public List<Suggestion> getUserSuggestions() {
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            throw new IllegalArgumentException("Authentication failed!");
        }
        return suggestionFacade.getUserSuggestions(user.getUserId());
    }

    /**
     * Endpoint for all Suggestions
     *
     * @return - list of all Suggestions
     */
    @GetMapping("/get")
    public List<Suggestion> getAllSuggestions() {
        return suggestionFacade.getAllSuggestions();
    }

    /**
     * Endpoint for logged-in user to add suggestion
      * @param sku - identifier of the product to add opinion to
     * @param description - content of the suggestion
     */
    @PostMapping("/add")
    public void addSuggestion(@RequestBody String sku, @RequestBody String description) {

        // check whether user is valid
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            throw new IllegalArgumentException("Authentication failed!");
        }
        // check whether product is valid
        Product product = productFacade.getProductBySku(sku);
        if (product == null) {
            throw new IllegalArgumentException("Product with given SKU does not exist");
        }
        // add suggestion
        suggestionFacade.addSuggestion(user.getUserId(), product, description);
    }

    @PutMapping("/reply")
    public void replyToSuggestion(@RequestBody Integer suggestionId, @RequestBody String suggestionStatus,
                                  @RequestBody String suggestionReply) {

        // check whether user is valid
        User user = userFacade.getUserByToken(getBearerTokenHeader());
        if (user == null) {
            throw new IllegalArgumentException("Authentication failed!");
        }
        if (!user.getAdmin()) {
            throw new IllegalArgumentException("Only admin can reply to suggestions!");
        }

        suggestionFacade.replySuggestion(user.getUserId(), suggestionId, suggestionStatus, suggestionReply);

    }


    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
    }

}
