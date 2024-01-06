package ch.fhnw.webec.exercise.repository;

import ch.fhnw.webec.exercise.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
}
