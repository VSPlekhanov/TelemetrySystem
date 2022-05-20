package org.azul.telemetry.data.repository;

import org.azul.telemetry.data.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <code>Role</code> JPA repository.
 *
 * @see Role
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
