package org.azul.telemetry.data.service;

import lombok.RequiredArgsConstructor;
import org.azul.telemetry.data.model.entity.RevokedToken;
import org.azul.telemetry.data.repository.RevokedTokenRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @see RevokedToken
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RevokedTokenService {
    private final RevokedTokenRepository repository;

    public boolean isRevoked(@NotNull String token) {
        return repository.findByToken(token).isPresent();
    }

    /**
     * Saves token.
     *
     * @param token token to save
     * @return whether token is saved or not
     */
    public boolean save(@NotNull RevokedToken token) {
        if (repository.findByToken(token.getToken()).isPresent()) {
            return false;
        }

        repository.save(token);

        return true;
    }
}
