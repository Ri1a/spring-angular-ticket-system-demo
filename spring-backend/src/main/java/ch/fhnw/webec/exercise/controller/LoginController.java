package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.User;
import ch.fhnw.webec.exercise.service.JwtService;
import ch.fhnw.webec.exercise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public LoginController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "/api/login")
    public ResponseEntity<String> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {
        User user = userService.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok("Bearer " + token);
        } else {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Login failed: Invalid username or password.");
        }
    }
}
