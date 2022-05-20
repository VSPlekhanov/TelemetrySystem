package org.azul.telemetry.web.api.admin;

import org.azul.telemetry.data.model.entity.Role;
import org.azul.telemetry.data.model.entity.User;
import org.azul.telemetry.data.service.RevokedTokenService;
import org.azul.telemetry.data.service.RoleService;
import org.azul.telemetry.data.service.UserService;
import org.azul.telemetry.web.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Currently disabled. See README.
 */
@Disabled
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AuthControllerE2ETest {
    private static final String base = "http://localhost:";
    private static final Map<String, String> credentials = Map.of(
            "username", "admin",
            "password", "admin"
    );
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RevokedTokenService revokedTokenService;

    @BeforeEach
    public void initDb() {
        roleService.saveRole(new Role(Role.ADMIN_ROLE_NAME));
        userService.saveUser(new User("admin", "admin", Set.of(new Role(Role.ADMIN_ROLE_NAME))));
    }

    @Test
    public void testLoginWithCorrectCredentials() {
        var response = template.postForEntity(
                base + port + "/api/admin/auth/login",
                credentials,
                Map.class
        );

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.hasBody());
        assertTrue(response.getBody().containsKey("token"));
    }

    @Test
    public void testLoginWithIncorrectCredentials() {
        var response = template.postForEntity(
                base + port + "/api/admin/auth/login",
                Map.of(
                        "username", "admin",
                        "password", "incorrect"
                ),
                Map.class
        );

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testRegister() {
        var responseData = template.postForObject(
                base + port + "/api/admin/auth/login",
                credentials,
                Map.class
        );

        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + responseData.get("token"));

        var newCredentials = Map.of(
                "username", "new_admin",
                "password", "pass"
        );

        var response = template.exchange(
                base + port + "/api/admin/auth/register",
                HttpMethod.POST,
                new HttpEntity<>(newCredentials, headers),
                String.class
        );

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(
                template.postForEntity(
                        base + port + "/api/admin/auth/login",
                        newCredentials,
                        Map.class
                ).getStatusCode(),
                HttpStatus.OK
        );
    }

    @Test
    public void testLoginLogout() {
        var responseData = template.postForObject(
                base + port + "/api/admin/auth/login",
                credentials,
                Map.class
        );

        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + responseData.get("token"));

        var response = template.exchange(
                base + port + "/api/admin/auth/logout",
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class
        );

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(
                template.exchange(
                        base + port + "/api/admin/auth/register",
                        HttpMethod.POST,
                        new HttpEntity<>(headers),
                        String.class
                ).getStatusCode(),
                HttpStatus.FORBIDDEN
        );
    }
}
