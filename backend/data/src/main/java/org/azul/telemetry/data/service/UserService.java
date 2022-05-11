package org.azul.telemetry.data.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.azul.telemetry.data.model.entity.User;
import org.azul.telemetry.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * .
 */
@Service
@Value
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    UserRepository repository;

    public List<User> getAll() {
        return repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }
}
