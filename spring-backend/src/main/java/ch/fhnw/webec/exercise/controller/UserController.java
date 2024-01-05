package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.User;
import ch.fhnw.webec.exercise.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User showUser(@PathVariable String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public User addUser(@Valid @RequestBody User user) {
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @PostMapping("/{id}/edit")
    public User editUser(@PathVariable String id, @Valid @RequestBody User user) {
        user.setId(id);
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userRepository.save(user);
    }

    @PostMapping("/{id}/delete")
    public void deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}
