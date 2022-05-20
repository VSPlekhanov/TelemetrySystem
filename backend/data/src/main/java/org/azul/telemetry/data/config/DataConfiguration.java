package org.azul.telemetry.data.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring configuration for data project.
 */
@ComponentScan("org.azul.telemetry.data")
@EnableJpaRepositories("org.azul.telemetry.data")
@EntityScan("org.azul.telemetry.data")
public class DataConfiguration {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
