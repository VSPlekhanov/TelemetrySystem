package org.azul.telemetry.agent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

//@ComponentScan("org.azul.telemetry")
@org.springframework.context.annotation.Configuration
public class Config {
    @Bean
    Validator getValidator(){
        Configuration<?> configure = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = configure.buildValidatorFactory();
        Validator validator = factory.getValidator();
        factory.close();
        return validator;
    }
}
