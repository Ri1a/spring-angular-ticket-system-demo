package ch.fhnw.webec.exercise.repository;

import ch.fhnw.webec.exercise.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
