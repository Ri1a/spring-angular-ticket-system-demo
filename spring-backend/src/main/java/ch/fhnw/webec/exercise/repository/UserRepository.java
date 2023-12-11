package ch.fhnw.webec.exercise.repository;

import ch.fhnw.webec.exercise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
