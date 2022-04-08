package org.azul.telemetry.data;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring configuration for data project.
 */
@Configuration
@ComponentScan("org.azul.telemetry.data")
@EnableJpaRepositories("org.azul.telemetry.data")
@EntityScan("org.azul.telemetry.data")
public class DataConfiguration { }
