package org.azul.telemetry.web.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Some utility functions for JWTs.
 */
@Log4j2
public class JwtUtils {
    private static Key SECRET;
    private final UserDetailsService userDetailsService;

    /**
     * .
     *
     * @param userDetailsService service to receive user data.
     * @param secretFilename     file with JWT signature
     * @throws RuntimeException if <code>IOException</code> was thrown while file reading
     */
    public JwtUtils(
            @NotNull UserDetailsService userDetailsService,
            @NotNull String secretFilename
    ) throws RuntimeException {
        this.userDetailsService = userDetailsService;

        try (Stream<String> lines = Files.lines(Paths.get(secretFilename))) {
            String b64Key = lines
                    .collect(Collectors.joining());
            byte[] keyBytes = Decoders.BASE64.decode(b64Key);
            SECRET = Keys.hmacShaKeyFor(keyBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates JWT token.
     *
     * @param authentication auth data to create token.
     * @return valid signed token. Token expires in one month
     */
    public String createToken(Authentication authentication) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        var calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(userDetails.getUsername())
                .setExpiration(calendar.getTime())
                .signWith(SECRET, SignatureAlgorithm.HS512)
                .compact();
    }


    /**
     * Gets username from JWT.
     *
     * @param token JWT token.
     * @return name of user for whom token was issued
     * @throws RuntimeException if token not valid
     * @see JwtUtils#validateJwtToken(String) to validate token
     */
    @Nullable
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Checks if token is valid.
     *
     * @param authToken token to validate.
     * @return <code>true</code> if token is valid
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("JWT is expired. Exception is: " + ex);
        } catch (Exception ex) {
            log.error("Failed to parse JWT. Exception is: " + ex);
        }
        return false;
    }
}
