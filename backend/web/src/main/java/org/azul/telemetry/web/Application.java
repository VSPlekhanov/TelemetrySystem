package org.azul.telemetry.web;

import java.util.List;
import org.azul.telemetry.data.DataConfiguration;
import org.azul.telemetry.data.User;
import org.azul.telemetry.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main app class.
 */
@RestController
@SpringBootApplication
@Import(DataConfiguration.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final UserService userService;

    @Autowired
    public Application(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> users() {
        return userService.getAll();
    }
}
