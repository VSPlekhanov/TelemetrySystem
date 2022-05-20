package org.azul.telemetry.data;

import org.azul.telemetry.data.config.DataConfiguration;
import org.azul.telemetry.data.model.entity.Role;
import org.azul.telemetry.data.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Currently disabled. See README.
 */
@Disabled
@DataJpaTest
@ContextConfiguration(classes = DataConfiguration.class)
public class RoleServiceTest {
    @Autowired
    private RoleService roleService;

    @BeforeEach
    public void initDb() {
        roleService.saveRole(new Role(Role.ADMIN_ROLE_NAME));
    }

    @Test
    public void testSave() {
        assertTrue(roleService.saveRole(new Role("ROLE_USER")));
        assertFalse(roleService.saveRole(new Role(Role.ADMIN_ROLE_NAME)));
    }

    @Test
    public void testNull() {
        assertThrows(
                DataIntegrityViolationException.class,
                () -> roleService.saveRole(new Role(null))
        );
    }
}
