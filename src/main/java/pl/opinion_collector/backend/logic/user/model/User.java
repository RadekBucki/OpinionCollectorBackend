package pl.opinion_collector.backend.logic.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User {
    @JsonIgnore
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private boolean isAdmin;
    @JsonIgnore
    private Set<Role> roles;
    private String pictureProfileUrl;

    public User(pl.opinion_collector.backend.database_communication.model.User user) {
        this.id = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPasswordHash();
        this.roles = createRoles(user.getAdmin());
        this.isAdmin = user.getAdmin();
        this.pictureProfileUrl = user.getProfilePictureUrl();
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, String pictureProfileUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pictureProfileUrl = pictureProfileUrl;
    }

    public User(Long id, String firstName, String lastName, String email, String password, String pictureProfileUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pictureProfileUrl = pictureProfileUrl;
    }

    public Set<Role> createRoles(boolean isAdmin) {
        Set<Role> roles = new HashSet<>();
        if (isAdmin) {
            roles.add(Role.ROLE_ADMIN);
        }
        roles.add(Role.ROLE_USER);
        return roles;
    }
}
