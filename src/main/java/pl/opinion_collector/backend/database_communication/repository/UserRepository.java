package pl.opinion_collector.backend.database_communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.opinion_collector.backend.database_communication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("update User u set u.firstName = ?2, u.lastName = ?3, u.email = ?4, u.passwordHash = ?5, u.profilePictureUrl = ?6, u.admin = ?7 where u.userId = ?1")
    void updateUser(Long userId, String firstName, String lastName, String email, String passwordHash, String profilePictureUrl, Boolean isAdmin);
}
