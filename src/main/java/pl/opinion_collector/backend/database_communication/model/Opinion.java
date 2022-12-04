package pl.opinion_collector.backend.database_communication.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

}
