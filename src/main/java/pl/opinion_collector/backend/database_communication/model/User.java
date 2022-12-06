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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @NonNull
    private String passwordHash;

    @NonNull
    private String profilePictureUrl;

    @NonNull
    private Boolean admin;

    @OneToMany(mappedBy = "userId", targetEntity = Opinion.class, cascade = CascadeType.ALL)
    private List<Opinion> opinions = new ArrayList<>();

    @OneToMany(mappedBy = "userId", targetEntity = Suggestion.class, cascade = CascadeType.ALL)
    private List<Suggestion> suggestions = new ArrayList<>();

    @OneToMany(mappedBy = "reviewerId", targetEntity = Suggestion.class, cascade = CascadeType.ALL)
    private List<Suggestion> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "authorId", targetEntity = Product.class, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

}
