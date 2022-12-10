package pl.opinion_collector.backend.logic.opinion.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Opinion {

    private Long opinionId;
    private String sku;
    private Integer opinionValue;
    private String description;
    private String pictureUrl;
    private List<String> advantages;
    private List<String> disadvantages;
    private Long userId;
    private Long productId;

    public Opinion(pl.opinion_collector.backend.database_communication.model.Opinion opinion) {
        this.opinionId = opinion.getOpinionId();
        this.sku = opinion.getProductId().getSku();
        this.opinionValue = opinion.getOpinionValue();
        this.description = opinion.getDescription();
        this.pictureUrl = opinion.getPictureUrl();
        this.advantages = opinion.getAdvantages();
        this.disadvantages = opinion.getDisadvantages();
        this.userId = opinion.getOpinionId();
        this.productId = opinion.getProductId().getProductId();
    }
}
