package pl.opinion_collector.backend.logic.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.opinion_collector.backend.database_communication.DatabaseCommunicationFacade;
import pl.opinion_collector.backend.database_communication.model.User;
import pl.opinion_collector.backend.logic.user.model.AppUser;
import pl.opinion_collector.backend.logic.user.security.jwt.JwtUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private DatabaseCommunicationFacade databaseCommunicationFacade;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class);
    @Override
    public List<AppUser> getAllUsers() {
        List<AppUser> users = new ArrayList<>();
        for (User user : databaseCommunicationFacade.getAllUsers()) {
            users.add(new AppUser(user));
        }
        return users;
    }

    @Override
    public AppUser getUserByToken(String token) {
        return findByEmail(jwtUtils.getUserNameFromJwtToken(token));
    }

    @Override
    public AppUser register(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (validateRegisterInput(email, password)) return null;
        return new AppUser(databaseCommunicationFacade.createUser(
                firstName,
                lastName,
                email,
                encoder.encode(password),
                profilePictureUrl,
                false));
    }
    @Override
    public AppUser registerAdmin(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (validateRegisterInput(email, password)) return null;
        return new AppUser(databaseCommunicationFacade.createUser(
                firstName,
                lastName,
                email,
                encoder.encode(password),
                profilePictureUrl,
                true));
    }

    @Override
    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    @Override
    public AppUser updateUser(Integer userId, String firstName, String lastName, String email, String passwordHash, String profilePictureUrl, Boolean isAdmin) {
        AppUser user;
        try {
            user = new AppUser(databaseCommunicationFacade.getUserById(Long.valueOf(userId)));
        } catch (EntityNotFoundException e) {
            logger.error("User with this id doesnt exist or u didnt provided any");
            return null;
        }
        if (databaseCommunicationFacade.getUserById(Long.valueOf(userId)) != null) {
            if (findByEmail(email) == null) {
                user = updateUserData(user, firstName, lastName, email, passwordHash, profilePictureUrl);
                databaseCommunicationFacade.updateUser(Long.valueOf(userId),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getPictureProfileUrl(),
                        (user.isAdmin() || isAdmin) && isAdmin);
            } else {
                logger.error("U cant change email that is taken");
                return null;
            }
        }
        return user;
    }
    public AppUser findByEmail(String email) {
        if (email == null || email.isBlank()) return null;
        for (User user : databaseCommunicationFacade.getAllUsers()) {
            if (user.getEmail().equals(email)) {
                return new AppUser(user);
            }
        }
        return null;
    }
    public AppUser updateUserData(AppUser user1, String firstName,
                                  String lastName, String email,
                                  String passwordHash, String profilePictureUrl) {
        return new AppUser(replaceIfDiffers(user1.getFirstName(), firstName),
                replaceIfDiffers(user1.getLastName(), lastName),
                replaceIfDiffers(user1.getEmail(), email),
                replaceIfDiffers(user1.getPassword(), passwordHash),
                replaceIfDiffers(user1.getPictureProfileUrl(), profilePictureUrl));
    }
    public String replaceIfDiffers(String s1, String s2) {
        if (s1 == null) return s2;
        if (s2 == null) return s1;
        return s2.isBlank() ? s1 : s2;
    }

    private boolean validateRegisterInput(String email, String password) {
        if (email != null && !email.contains("@") && !email.contains(".")
                && password != null && !password.isBlank()) {
            logger.error("U have to input valid email and password to register");
            return true;
        } else if (findByEmail(email) != null) {
            logger.error("Email is already used");
            return true;
        }
        return false;
    }
}
