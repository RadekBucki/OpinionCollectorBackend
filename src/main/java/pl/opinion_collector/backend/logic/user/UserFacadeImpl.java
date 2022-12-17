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
import pl.opinion_collector.backend.logic.exception.type.ForbiddenException;
import pl.opinion_collector.backend.logic.exception.type.ParameterException;
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
    public List<User> getAllUsers() {
        return new ArrayList<>(databaseCommunicationFacade.getAllUsers());
    }

    @Override
    public User getUserByToken(String token) {
        User user = findByEmail(jwtUtils.getUserNameFromJwtToken(token));
        if (token.isBlank() || user == null)
            throw new ParameterException("Can't get user by token");
        return user;
    }

    @Override
    public User register(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (validateRegisterInput(email, password)) {
            throw new ParameterException("Wrong register input or email is taken");
        }
        return databaseCommunicationFacade.createUser(
                firstName,
                lastName,
                email,
                encoder.encode(password),
                profilePictureUrl,
                false);
    }
    @Override
    public User registerAdmin(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (validateRegisterInput(email, password)) {
            throw new ParameterException("Wrong register input or email is taken");
        }
        return databaseCommunicationFacade.createUser(
                firstName,
                lastName,
                email,
                encoder.encode(password),
                profilePictureUrl,
                true);
    }

    @Override
    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    @Override
    public User updateUser(Integer userId, String firstName, String lastName, String email, String passwordHash,
                           String profilePictureUrl, Boolean isAdmin) {
        User user = databaseCommunicationFacade.getUserById(Long.valueOf(userId));
        if (user == null) {
            throw new EntityNotFoundException("User with this id doesn't exist");
        }

        if (email == null || findByEmail(email) == null || user.getEmail().equals(email)) {
            updateUserData(user, firstName, lastName, email, passwordHash, profilePictureUrl, isAdmin);
            databaseCommunicationFacade.updateUser(Long.valueOf(userId),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPasswordHash(),
                    user.getProfilePictureUrl(),
                    user.getAdmin());
        } else {
            logger.error("U can't change email that is taken");
            throw new ForbiddenException("U can't change to email that is taken");
        }

        return user;
    }

    public void updateUserData(User user, String firstName,
                               String lastName, String email,
                               String passwordHash, String profilePictureUrl,
                               Boolean isAdmin) {
        user.setFirstName(replaceIfDiffers(user.getFirstName(), firstName));
        user.setLastName(replaceIfDiffers(user.getLastName(), lastName));
        user.setEmail(replaceIfDiffers(user.getEmail(), email));
        user.setPasswordHash(encoder.encode(replaceIfDiffers(user.getPasswordHash(), passwordHash)));
        user.setProfilePictureUrl(replaceIfDiffers(user.getProfilePictureUrl(), profilePictureUrl));
        user.setAdmin((user.getAdmin() || isAdmin) && isAdmin);
    }
    public User findByEmail(String email) {
        return databaseCommunicationFacade.getAllUsers().stream()
                .filter(user -> email.equals(user.getEmail())).findAny().orElse(null);
    }
    public String replaceIfDiffers(String s1, String s2) {
        if (s1 == null) return s2;
        if (s2 == null) return s1;
        return s2.isBlank() ? s1 : s2;
    }

    private boolean validateRegisterInput(String email, String password) {
        if (!email.contains("@") && !email.contains(".") || password == null) {
            logger.error("U have to input valid email and password to register");
            return true;
        } else if (findByEmail(email) != null) {
            logger.error("Email is already used");
            return true;
        }
        return false;
    }
}
