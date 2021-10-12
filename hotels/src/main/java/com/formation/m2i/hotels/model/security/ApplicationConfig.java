package com.formation.m2i.hotels.model.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class ApplicationConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImplementation userDetailsService;

    public ApplicationConfig(UserDetailsServiceImplementation userDetailsS) {
        this.userDetailsService = userDetailsS;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    public String encodePassword(String password) {
        return passwordEncoder().encode(password);
    }


    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and()
                    .antMatcher("/api/**")
                    .csrf()
                    .disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // On utilise pas les sessions, toute req est déconnectée suite à l'exécution
                    .and().authorizeRequests(authorize -> authorize
                            .anyRequest().hasRole("ADMIN")
                    )
                    .httpBasic();
        }
    }
}
