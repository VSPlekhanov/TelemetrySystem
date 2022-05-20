package org.azul.telemetry.web.configuration;

import org.azul.telemetry.web.auth.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Configuration for non security beans.
 */
@Configuration
public class BeanConfiguration {
    private final UserDetailsService userDetailsService;

    @Autowired
    BeanConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtUtils jwtUtils(@Value("${application.secret-key-file}") String secretFileName) {
        return new JwtUtils(userDetailsService, secretFileName);
    }
}
