package pl.opinion_collector.backend.logic.user;

import pl.opinion_collector.backend.database_communication.model.User;

import java.util.List;

public interface UserFacade {
    List<User> getAllUsers();

    User getUserByToken(String token);

    User register(String firstName, String lastName, String email, String password, String profilePictureUrl);

    User registerAdmin(
            String firstName,
            String lastName,
            String email,
            String password,
            String profilePictureUrl
    );

    String login(String email, String password);

    User updateUser(
            Integer userId,
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String profilePictureUrl,
            Boolean isAdmin
    );
}
