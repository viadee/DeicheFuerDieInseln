package de.viadee.oauth2.authorization.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static java.util.Arrays.asList;

/**
 * Configuration of authentication manager, which is used by OAuth2
 * authorization server to provide user authentication.
 */
@Configuration
public class AuthenticationManagerConfiguration {

    /**
     * Provide a user details service, which will be used by the auto created authentication manager.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(new User("admin", passwordEncoder().encode("secret"),
                asList( new SimpleGrantedAuthority("ZUGRIFF_PRODUKT_KATALOG"),
                        new SimpleGrantedAuthority("GET_USER"),
                        new SimpleGrantedAuthority("POST_USER"))));

        return userDetailsManager;
    }

    /**
     * Provide BCrypt password encoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
