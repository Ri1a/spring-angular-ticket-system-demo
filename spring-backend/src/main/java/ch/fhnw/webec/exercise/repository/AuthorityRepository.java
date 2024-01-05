package ch.fhnw.webec.exercise.repository;

import ch.fhnw.webec.exercise.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
