package org.azul.telemetry.web.configuration;

import lombok.RequiredArgsConstructor;
import org.azul.telemetry.data.model.entity.Role;
import org.azul.telemetry.web.auth.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${application.environment}")
    private String envMode;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenAuthenticationFilter authenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(conf -> conf
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeRequests(requests -> {
                requests.antMatchers("/api/admin/auth/login/**").permitAll();

                if (envMode.equals(ApplicationMode.DEVELOPMENT)) {
                    requests.antMatchers("/api/admin/auth/register/**").permitAll();
                } else if (envMode.equals(ApplicationMode.WITHOUT_AUTH)) {
                    requests.antMatchers("/**").permitAll();
                } else {
                    requests
                        .antMatchers("/api/admin/**").hasAuthority(Role.ADMIN_ROLE_NAME)
                        .antMatchers("/api/**").authenticated();
                }
            })
            .addFilterBefore(
                authenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
