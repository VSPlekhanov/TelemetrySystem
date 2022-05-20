package org.azul.telemetry.data;

import org.azul.telemetry.data.config.DataConfiguration;
import org.azul.telemetry.data.model.entity.RevokedToken;
import org.azul.telemetry.data.service.RevokedTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Currently disabled. See README.
 */
@Disabled
@DataJpaTest
@ContextConfiguration(classes = DataConfiguration.class)
public class RevokedTokenServiceTest {
    @Autowired
    private RevokedTokenService revokedTokenService;

    @BeforeEach
    public void initDb() {
        revokedTokenService.save(new RevokedToken("123"));
    }

    @Test
    public void testIsRevoked() {
        assertTrue(revokedTokenService.isRevoked("123"));
        assertFalse(revokedTokenService.isRevoked("456"));
    }

    @Test
    public void testSave() {
        assertTrue(revokedTokenService.save(new RevokedToken("newToken")));
        assertFalse(revokedTokenService.save(new RevokedToken("123")));
    }

    @Test
    public void testNull() {
        assertThrows(
                Exception.class,
                () -> revokedTokenService.save(new RevokedToken(null))
        );
        assertThrows(
                Exception.class,
                () -> revokedTokenService.isRevoked(null)
        );
    }
}
