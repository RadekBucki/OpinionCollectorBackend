package pl.opinion_collector.backend.logic.user.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.opinion_collector.backend.database_communication.communication.UserDatabaseCommunication;
import pl.opinion_collector.backend.logic.user.model.User;
import pl.opinion_collector.backend.logic.user.security.jwt.AuthTokenFilter;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private UserDatabaseCommunication userDatabaseCommunication;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = new User(findUserByUsername(username));
            return UserDetailsImpl.build(user);
        } catch (UsernameNotFoundException e) {
            logger.error("Error while loading user: {}", e.getMessage());
        }
        return null;
    }
    public pl.opinion_collector.backend.database_communication.model.User findUserByUsername(String email) {
        return  userDatabaseCommunication.getAllUsers().stream().filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("No user was found"));
    }


}