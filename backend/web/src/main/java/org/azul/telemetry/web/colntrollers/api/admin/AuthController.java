package org.azul.telemetry.web.colntrollers.api.admin;

import lombok.RequiredArgsConstructor;
import org.azul.telemetry.data.model.entity.RevokedToken;
import org.azul.telemetry.data.model.entity.Role;
import org.azul.telemetry.data.model.entity.User;
import org.azul.telemetry.data.service.RevokedTokenService;
import org.azul.telemetry.data.service.UserService;
import org.azul.telemetry.web.auth.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * API controller for auth to admin control panel.
 */
@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RevokedTokenService revokedTokenService;
    private final UserService userService;

    /**
     * Logins admin by it`s login and password.
     *
     * @param request request body.
     * @return JSON with JWT to access API
     */
    @PostMapping(
            value = "/login",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public Map<String, String> login(@RequestBody CredentialsRequestBody request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username, request.password)
        );

        if (
                authentication.isAuthenticated()
                        && authentication.getAuthorities()
                        .stream()
                        .noneMatch(s -> s.getAuthority().equals(Role.ADMIN_ROLE_NAME))
        ) {
            authentication.setAuthenticated(false);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.createToken(authentication);

        return Map.of(
                "token", jwt
        );
    }

    /**
     * Performs logout via revoking token.
     */
    @PostMapping(value = "/logout")
    public void logout() {
        String jwt = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();

        revokedTokenService.save(new RevokedToken(jwt));
    }

    /**
     * Registers new admin.
     *
     * @param body Login and password for new admin.
     */
    @PostMapping(
            value = "/register",
            consumes = APPLICATION_JSON_VALUE
    )
    public void register(@RequestBody CredentialsRequestBody body) {
        if (!userService.saveUser(
                new User(body.username, body.password, Set.of(new Role(Role.ADMIN_ROLE_NAME)))
        )) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }

    private static class CredentialsRequestBody {
        public String username;
        public String password;
    }
}
