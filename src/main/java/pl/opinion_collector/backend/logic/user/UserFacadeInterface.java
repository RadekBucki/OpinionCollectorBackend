package pl.opinion_collector.backend.logic.user;

import pl.opinion_collector.backend.database_communication.UserInterface;

import java.util.List;

public interface UserFacadeInterface {
    List<UserInterface> getAllUsers();

    UserInterface getUserByToken(String token);

    UserInterface register(String firstName, String lastName, String email, String password, String profilePictureUrl);

    UserInterface registerAdmin(
            String firstName,
            String lastName,
            String email,
            String password,
            String profilePictureUrl
    );

    String login(String email, String password);

    UserInterface updateUser(
            Integer userId,
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String profilePictureUrl,
            Boolean isAdmin
    );
}
