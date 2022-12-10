package pl.opinion_collector.backend.database_communication.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
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

    @OneToOne
    private Review review;

}
