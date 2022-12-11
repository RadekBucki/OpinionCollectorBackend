package pl.opinion_collector.backend.logic.user.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import pl.opinion_collector.backend.database_communication.model.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWrapper {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isAdmin;
    private Set<Role> roles;
    private String pictureProfileUrl;

    public UserWrapper(User user) {
        this.id = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPasswordHash();
        this.roles = createRoles(user.getAdmin());
        this.isAdmin = user.getAdmin();
        this.pictureProfileUrl = user.getProfilePictureUrl();
    }

    public UserWrapper(String firstName, String lastName, String email, String password, String pictureProfileUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pictureProfileUrl = pictureProfileUrl;
    }

    public UserWrapper(Long id, String firstName, String lastName, String email, String password, String pictureProfileUrl) {
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
