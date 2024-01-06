package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.Authority;
import ch.fhnw.webec.exercise.model.User;
import ch.fhnw.webec.exercise.repository.AuthorityRepository;
import ch.fhnw.webec.exercise.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public UserController(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public User showUser(@PathVariable String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/add")
    public User addUser(@Valid @RequestBody User user) {
        user.setId(UUID.randomUUID().toString());

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password must be at least 6 characters long.");
        }
        setUserAuthorities(user);
        return userRepository.save(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{id}/edit")
    public User editUser(@PathVariable String id, @Valid @RequestBody User user) {
        user.setId(id);
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        setUserAuthorities(user);
        return userRepository.save(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{id}/delete")
    public void deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting user.");
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
    }

    private Set<Authority> setUserAuthorities(User user) {
        Set<Authority> authorities = new HashSet<>();
        for (GrantedAuthority authorityName : user.getAuthorities()) {
            Authority existingAuthority = authorityRepository.findByName(authorityName.toString());
            if (existingAuthority != null) {
                authorities.add(existingAuthority);
            }
        }
        user.setAuthorities(authorities);
        return authorities;
    }
}
