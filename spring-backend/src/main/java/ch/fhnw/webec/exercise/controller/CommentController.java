package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.Comment;
import ch.fhnw.webec.exercise.repository.CommentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/comments") // Base path for comments
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment showComment(@PathVariable String id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public Comment addComment(@Valid @RequestBody Comment comment) {
        comment.setId(UUID.randomUUID().toString());
        return commentRepository.save(comment);
    }

    @PostMapping("/{id}/edit")
    public Comment editComment(@PathVariable String id, @Valid @RequestBody Comment comment) {
        comment.setId(id);
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return commentRepository.save(comment);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteComment(@PathVariable String id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        commentRepository.deleteById(id);
    }
}
