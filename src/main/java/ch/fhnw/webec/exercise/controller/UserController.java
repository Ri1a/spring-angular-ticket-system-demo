package ch.fhnw.webec.exercise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "User");

        return "user";
    }
}
