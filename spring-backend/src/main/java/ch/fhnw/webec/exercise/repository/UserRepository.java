package ch.fhnw.webec.exercise.repository;

import ch.fhnw.webec.exercise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    @Query(value = "SELECT * FROM user", nativeQuery = true)
    List<User> getAllUser();
}
