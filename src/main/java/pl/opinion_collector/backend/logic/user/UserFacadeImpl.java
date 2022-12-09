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
import pl.opinion_collector.backend.database_communication.communication.UserDatabaseCommunication;
import pl.opinion_collector.backend.logic.user.model.Role;
import pl.opinion_collector.backend.logic.user.model.User;
import pl.opinion_collector.backend.logic.user.security.jwt.JwtUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private UserDatabaseCommunication userDatabaseCommunication;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class);
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (pl.opinion_collector.backend.database_communication.model.User user :
                userDatabaseCommunication.getAllUsers()) {
            users.add(new User(user));
        }
        return users;
    }

    @Override
    public User getUserByToken(String token) {
        return findByEmail(jwtUtils.getUserNameFromJwtToken(token));
    }

    @Override
    public User register(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (findByEmail(email) != null) {
            logger.error("USER WITH THIS EMAIL IS ALREADY EXISTS");
            return null;
        }
        userDatabaseCommunication.createUser(
                firstName,
                lastName,
                email,
                encoder.encode(password),
                profilePictureUrl,
                false
        );
        return new User(firstName, lastName, email, password, profilePictureUrl);
    }

    @Override
    public User registerAdmin(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (findByEmail(email) != null) {
            logger.error("USER WITH THIS EMAIL IS ALREADY EXISTS");
            return null;
        }
        userDatabaseCommunication.createUser(
                firstName,
                lastName,
                email,
                encoder.encode(password),
                profilePictureUrl,
                true
        );
        return new User(firstName, lastName, email, password, profilePictureUrl);
    }

    @Override
    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    @Override
    public User updateUser(Integer userId, String firstName, String lastName, String email, String passwordHash, String profilePictureUrl, Boolean isAdmin) {
        User user;
        if (userDatabaseCommunication.getUserById(Long.valueOf(userId)) != null) {
            user = new User(userDatabaseCommunication.getUserById(Long.valueOf(userId)));
        } else {
            logger.error("USER WITH THIS ID DOESNT EXIST");
            return null;
        }
        if (userDatabaseCommunication.getUserById(Long.valueOf(userId)) != null) {
            if (findByEmail(email) == null || findByEmail(email).getId() == userId.longValue()) {
                System.out.println(user.getPassword() + " " + passwordHash);
                userDatabaseCommunication.updateUser(Long.valueOf(userId),
                        replaceIfDiffers(user.getFirstName(), firstName),
                        replaceIfDiffers(user.getLastName(), lastName),
                        replaceIfDiffers(user.getEmail(), email),
                        encoder.encode(replaceIfDiffers(user.getPassword(), passwordHash)),
                        replaceIfDiffers(user.getPictureProfileUrl(), profilePictureUrl),
                        (user.getRoles().contains(Role.ROLE_ADMIN) || isAdmin) && isAdmin);
            } else {
                logger.error("U CANT CHANGE EMAIL THAT IS TAKEN");
                return null;
            }
        }
        return new User(Long.valueOf(userId), firstName, lastName, email, passwordHash, profilePictureUrl);
    }
    public User findByEmail(String email) {
        if (email == null || email.isBlank()) return null;
        for (pl.opinion_collector.backend.database_communication.model.User user : userDatabaseCommunication.getAllUsers()) {
            if (user.getEmail().equals(email)) {
                return new User(user);
            }
        }
        return null;
    }
    public String replaceIfDiffers(String s1, String s2) {
        if (s1 == null) return s2;
        if (s2 == null) return s1;
        return s2.isBlank() ? s1 : s2;
    }
}
