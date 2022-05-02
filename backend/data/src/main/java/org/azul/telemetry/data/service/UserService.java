package org.azul.telemetry.data.service;

import java.util.List;

import org.azul.telemetry.data.model.entity.User;
import org.azul.telemetry.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * .
 */
@Service
public class UserService {
    final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }
}
