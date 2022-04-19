package org.azul.telemetry.web.controllers;

import java.util.List;

import lombok.Data;
import org.azul.telemetry.data.User;
import org.azul.telemetry.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Data
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> users() {
        return userService.getAll();
    }

    @PostMapping("/add")
    public User addUsers(@PathVariable(name = "name") String name) {
        User user = new User();
        user.setName(name);
        return userService.save(user);
    }
}
