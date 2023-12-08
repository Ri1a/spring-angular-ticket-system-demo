package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.User;
import ch.fhnw.webec.exercise.repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {

    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // all users
    @RequestMapping(value = "/api/user/getAllUser", method = RequestMethod.GET)
    public List<User> getAllUser() {
        return userRepository.getAllUser();
    }

    // delete user
    @PostMapping(value = "/api/deleteUser/{userId}")
    public void deleteUser(@PathVariable("userId") String userId){
        userRepository.deleteById(userId);
    }

    // save user
    @PostMapping(value = "/api/saveUser")
    public User saveUser(@RequestBody User user){
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    // update user
    @PostMapping(value = "/api/updateUser")
    public User updateUser(@RequestBody User user){
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return userRepository.save(user);
    }
}
