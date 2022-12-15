package pl.opinion_collector.backend.database_communication.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = "opinion")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opinion_id")
    private Long opinionId;

    @NonNull
    private Integer opinionValue;

    @NonNull
    private String description;

    @NonNull
    private String pictureUrl;

    @NonNull
    @Type(type = "json")
    private List<String> advantages;

    @NonNull
    @Type(type = "json")
    private List<String> disadvantages;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

}
