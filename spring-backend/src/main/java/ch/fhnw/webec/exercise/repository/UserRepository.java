package ch.fhnw.webec.exercise.repository;

import ch.fhnw.webec.exercise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
        SELECT DISTINCT user FROM User user
        WHERE lower(user.name) LIKE lower(concat('%', :search, '%'))
    """)
    List<User> findBySearch(@Param("search") String search);
}

