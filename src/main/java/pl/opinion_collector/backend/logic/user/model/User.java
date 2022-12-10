package pl.opinion_collector.backend.logic.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

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

    public User() {}

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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<Role> createRoles(boolean isAdmin) {
        Set<Role> roles = new HashSet<>();
        if (isAdmin) {
            roles.add(Role.ROLE_ADMIN);
        } roles.add(Role.ROLE_USER);
        return roles;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public String getPictureProfileUrl() {
        return pictureProfileUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPictureProfileUrl(String pictureProfileUrl) {
        this.pictureProfileUrl = pictureProfileUrl;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
