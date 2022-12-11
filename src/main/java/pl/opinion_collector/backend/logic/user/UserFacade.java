package pl.opinion_collector.backend.logic.user;

import pl.opinion_collector.backend.logic.user.model.AppUser;
import java.util.List;

public interface UserFacade {
    List<AppUser> getAllUsers();

    AppUser getUserByToken(String token);

    AppUser register(String firstName, String lastName, String email, String password, String profilePictureUrl);

    AppUser registerAdmin(
            String firstName,
            String lastName,
            String email,
            String password,
            String profilePictureUrl
    );

    String login(String email, String password);

    AppUser updateUser(
            Integer userId,
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String profilePictureUrl,
            Boolean isAdmin
    );
}
