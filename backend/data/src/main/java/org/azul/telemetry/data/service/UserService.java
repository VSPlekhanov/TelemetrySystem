package org.azul.telemetry.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.azul.telemetry.data.model.entity.Role;
import org.azul.telemetry.data.model.entity.User;
import org.azul.telemetry.data.repository.RoleRepository;
import org.azul.telemetry.data.repository.UserRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Spring service for <code>User</code> entity.
 *
 * @see User
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Saves user with given role.
     *
     * @param user fully populates user to save. Password will we hashed before saving
     * @return whether user is saved or not.
     */
    @Contract(mutates = "param1")
    public boolean saveUser(@NotNull User user) {
        Optional<User> userFromDb = userRepository.findByName(user.getName());

        if (userFromDb.isPresent()) {
            log.error("Cannot save user " + user.getName() + ". Already exists");
            return false;
        }

        var roles = new HashSet<Role>();

        for (var role : user.getRoles()) {
            var roleFromDb = roleRepository.findByName(role.getName());

            if (roleFromDb.isEmpty()) {
                log.error("Cannot save user "
                        + user.getName()
                        + ". Role "
                        + role.getName()
                        + " does not exists"
                );
                return false;
            }

            roles.add(roleFromDb.get());
        }

        user.setRoles(roles).setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    /**
     * Gets user by name.
     *
     * @param name username to find.
     * @return <code>User</code> object or <code>null</code> if it is not found.
     */
    @Nullable
    public User getByName(@NotNull String name) {
        return userRepository
                .findByName(name)
                .orElse(null);
    }

    public User getById(@NotNull Long id) {
        return userRepository.getById(id);
    }

    @NotNull
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User " + username + " not found")
                );
    }
}
