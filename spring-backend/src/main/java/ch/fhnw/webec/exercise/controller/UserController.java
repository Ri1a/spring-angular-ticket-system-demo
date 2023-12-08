package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("name", "User");

        return "user";
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable int id, Model model) {
        model.addAttribute("user", this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        return "user/show";
    }
}
