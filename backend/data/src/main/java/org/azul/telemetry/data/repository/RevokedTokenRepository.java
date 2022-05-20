package org.azul.telemetry.data.repository;

import org.azul.telemetry.data.model.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <code>RevokedToken</code> JPA repository.
 *
 * @see RevokedToken
 */
@Repository
public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long> {
    Optional<RevokedToken> findByToken(String token);
}
