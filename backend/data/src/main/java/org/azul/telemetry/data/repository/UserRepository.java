package org.azul.telemetry.data.repository;

import org.azul.telemetry.data.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * .
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
