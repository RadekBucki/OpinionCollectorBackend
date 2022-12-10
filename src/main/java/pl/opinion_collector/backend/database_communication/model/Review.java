package pl.opinion_collector.backend.database_communication.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @NonNull
    private String status;

    @NonNull
    private String reply;

    @OneToOne
    private Suggestion suggestion;

}
