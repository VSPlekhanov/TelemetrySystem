package org.azul.telemetry.web;

import org.azul.telemetry.data.config.DataConfiguration;
import org.azul.telemetry.web.configuration.BeanConfiguration;
import org.azul.telemetry.web.configuration.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main app class.
 */
@SpringBootApplication
@Import({DataConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
