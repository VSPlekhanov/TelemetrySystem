package org.azul.telemetry.data.repository;

import org.azul.telemetry.data.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <code>User</code> JPA repository.
 *
 * @see User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
