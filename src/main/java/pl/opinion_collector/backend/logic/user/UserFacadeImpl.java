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
import pl.opinion_collector.backend.logic.user.dto.Mapper;
import pl.opinion_collector.backend.logic.user.wrapper.UserWrapper;
import pl.opinion_collector.backend.logic.user.security.jwt.JwtUtils;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLDataException;
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
    public List<UserWrapper> getAllUsers() {
        List<UserWrapper> users = new ArrayList<>();
        for (User user : databaseCommunicationFacade.getAllUsers()) {
            users.add(new UserWrapper(user));
        }
        return users;
    }

    @Override
    public UserWrapper getUserByToken(String token) {
        return findByEmail(jwtUtils.getUserNameFromJwtToken(token));
    }

    @Override
    public UserWrapper register(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (validateRegisterInput(email, password)) return null;
        databaseCommunicationFacade.createUser(
                firstName,
                lastName,
                email,
                encoder.encode(password),
                profilePictureUrl,
                false
        );
        return new UserWrapper(firstName, lastName, email, password, profilePictureUrl);
    }
    @Override
    public UserWrapper registerAdmin(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (validateRegisterInput(email, password)) return null;
        databaseCommunicationFacade.createUser(
                firstName,
                lastName,
                email,
                encoder.encode(password),
                profilePictureUrl,
                true
        );
        return new UserWrapper(firstName, lastName, email, password, profilePictureUrl);
    }

    @Override
    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    @Override
    public UserWrapper updateUser(Integer userId, String firstName, String lastName, String email, String passwordHash, String profilePictureUrl, Boolean isAdmin) {
        UserWrapper user;
        try {
            user = new UserWrapper(databaseCommunicationFacade.getUserById(Long.valueOf(userId)));
        } catch (EntityNotFoundException e) {
            logger.error("User with this id doesnt exist or u didnt provided any");
            return null;
        }

        if (databaseCommunicationFacade.getUserById(Long.valueOf(userId)) != null) {
            if (findByEmail(email) == null) {
                databaseCommunicationFacade.updateUser(Long.valueOf(userId),
                        replaceIfDiffers(user.getFirstName(), firstName),
                        replaceIfDiffers(user.getLastName(), lastName),
                        replaceIfDiffers(user.getEmail(), email),
                        encoder.encode(replaceIfDiffers(user.getPassword(), passwordHash)),
                        replaceIfDiffers(user.getPictureProfileUrl(), profilePictureUrl),
                        (user.isAdmin() || isAdmin) && isAdmin);
            } else {
                logger.error("U cant change email that is taken");
                return null;
            }
        }
        return new UserWrapper(Long.valueOf(userId), firstName, lastName, email, passwordHash, profilePictureUrl);
    }
    public UserWrapper findByEmail(String email) {
        if (email == null || email.isBlank()) return null;
        for (User user : databaseCommunicationFacade.getAllUsers()) {
            if (user.getEmail().equals(email)) {
                return new UserWrapper(user);
            }
        }
        return null;
    }
    public String replaceIfDiffers(String s1, String s2) {
        if (s1 == null) return s2;
        if (s2 == null) return s1;
        return s2.isBlank() ? s1 : s2;
    }

    private boolean validateRegisterInput(String email, String password) {
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            logger.error("U have to input email and password to register");
            return true;
        } else if (findByEmail(email) != null) {
            logger.error("Email is already used");
            return true;
        }
        return false;
    }
}
