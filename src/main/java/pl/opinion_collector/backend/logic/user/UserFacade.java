package pl.opinion_collector.backend.logic.user;



import pl.opinion_collector.backend.logic.user.wrapper.UserWrapper;

import java.util.List;

public interface UserFacade {
    List<UserWrapper> getAllUsers();

    UserWrapper getUserByToken(String token);

    UserWrapper register(String firstName, String lastName, String email, String password, String profilePictureUrl);

    UserWrapper registerAdmin(
            String firstName,
            String lastName,
            String email,
            String password,
            String profilePictureUrl
    );

    String login(String email, String password);

    UserWrapper updateUser(
            Integer userId,
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String profilePictureUrl,
            Boolean isAdmin
    );
}
