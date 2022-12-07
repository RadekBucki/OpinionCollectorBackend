package pl.opinion_collector.backend.logic.opinion;

import org.springframework.web.bind.annotation.*;
import pl.opinion_collector.backend.database_communication.model.Opinion;
import pl.opinion_collector.backend.database_communication.model.Product;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.logic.user.UserFacade;
import java.util.List;

@RequestMapping("/opinions")
public class OpinionController {

    private OpinionService service;
    private UserFacade userFacade;

    @GetMapping("/user")
    public List<Opinion> getUserOpinions() {
        String token = "das";
        Long user = userFacade.getUserByToken(token).getUserId();
        if (user == null) {
            throw new IllegalArgumentException("Authentication failed!");
        }
        return service.getUserOpinions(user);
    }

    @GetMapping("/product")
    public List<Opinion> getProductOpinions(@RequestParam String sku) {
        return service.getProductOpinions(sku);
    }

    @PostMapping("/add")
    public void addOpinion(@RequestParam String sku, @RequestBody OpinionDto opinionDto) {
        service.addProductOpinion(opinionDto.opinionId, sku, opinionDto.opinionValue, opinionDto.description,
                opinionDto.pictureUrl, opinionDto.advantages, opinionDto.disadvantages);
    }

    private class OpinionDto {
        private Long opinionId;
        private Integer opinionValue;
        private String description;
        private String pictureUrl;
        private List<String> advantages;
        private List<String> disadvantages;
        private User userId;
        private Product productId;
    }



 }
