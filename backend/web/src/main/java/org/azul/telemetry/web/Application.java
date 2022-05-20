package org.azul.telemetry.web;

import org.azul.telemetry.data.config.DataConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main app class.
 */
@SpringBootApplication
@Import({BeanConfiguration.class, DataConfiguration.class, SecurityConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
