package pl.opinion_collector.backend.database_communication.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "suggestion")
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestion_id")
    private Long suggestionId;

    @NonNull
    private String description;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @ManyToOne
    private User reviewerId;

    @OneToOne(cascade = CascadeType.ALL)
    private Review review;

}
