package pl.opinion_collector.backend.database_communication.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NonNull
    private String sku;

    @NonNull
    private String name;

    @NonNull
    private String pictureUrl;

    @NonNull
    private String description;

    @NonNull
    private Boolean visible;

    @Column(name = "opinion_avg")
    private Double opinionAvg;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User authorId;

    @OneToMany(mappedBy = "productId", targetEntity = Opinion.class, cascade = CascadeType.ALL)
    private List<Opinion> opinions = new ArrayList<>();

    @OneToMany(mappedBy = "productId", targetEntity = Suggestion.class, cascade = CascadeType.ALL)
    private List<Suggestion> suggestions = new ArrayList<>();

    @ManyToMany(mappedBy = "products", targetEntity = Category.class, cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    @PostLoad
    public void calculateAvgOpinion() {
        opinionAvg = opinions.stream()
                .mapToDouble(Opinion::getOpinionValue)
                .average()
                .orElse(0.0);
    }
}
