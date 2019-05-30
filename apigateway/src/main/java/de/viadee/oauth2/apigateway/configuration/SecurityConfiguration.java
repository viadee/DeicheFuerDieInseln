package de.viadee.oauth2.apigateway.configuration;

import de.viadee.oauth2.apigateway.oauth2.OAuth2PasswordAuthenticationProvider;
import de.viadee.oauth2.apigateway.oauth2.Oauth2PasswordAuthenticationProperties;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static java.util.Collections.singletonList;

/**
 * Security configuration of API gateway. API gateway is configured for session management and login/logout.
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(Oauth2PasswordAuthenticationProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final Oauth2PasswordAuthenticationProperties properties;

    @Autowired
    public SecurityConfiguration(Oauth2PasswordAuthenticationProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        // Configure formbased login using /login.
        // Only use simple HTTP status codes as response instead of redirects to utilize usage by single page webapp
        // (e.g. using http fetch api).
        http
            .formLogin()
                .loginPage("/login")
                .successHandler((request, reponse, authentication) -> reponse.setStatus(HttpStatus.SC_OK))
                .failureHandler((request, reponse, exception) -> reponse.setStatus(HttpStatus.SC_FORBIDDEN))
        // Configure logout using /logout
        .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, reponse, authentication) -> reponse.setStatus(HttpStatus.SC_OK))
        // To focus only on OAuth and login handling, CSRF is disabled here.
        // For production use, CSRF should also be considered.
        .and()
            .csrf()
                .disable()
        ;
        // @formatter:on
    }

    /**
     * Configure our custom authentication provider to forward login to oauth2
     * authorization server.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() {
        return new ProviderManager(singletonList(new OAuth2PasswordAuthenticationProvider(properties)));
    }

}