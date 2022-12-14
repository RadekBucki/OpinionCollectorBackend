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
        return findByEmail(jwtUtils.getUserNameFromJwtToken(token));
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
        User user = findByEmail(email);
        if (user == null)
            throw new EntityNotFoundException("User with this id doesnt exist");

        user.setFirstName(replaceIfDiffers(user.getFirstName(), firstName));
        user.setLastName(replaceIfDiffers(user.getLastName(), lastName));
        user.setPasswordHash(replaceIfDiffers(user.getPasswordHash(), passwordHash));
        user.setProfilePictureUrl(replaceIfDiffers(user.getProfilePictureUrl(), profilePictureUrl));
        user.setAdmin((user.getAdmin() || isAdmin) && isAdmin);

        databaseCommunicationFacade.updateUser(user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encoder.encode(user.getPasswordHash()),
                user.getProfilePictureUrl(),
                user.getAdmin());
        return user;
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
