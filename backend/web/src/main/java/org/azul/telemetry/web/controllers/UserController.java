package org.azul.telemetry.web.controllers;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.azul.telemetry.data.model.entity.User;
import org.azul.telemetry.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
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
