package de.viadee.oauth2.produkte.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.http.HttpMethod.GET;

/**
 * Configuration of OAuth2 resource server.
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    /**
     * Security configuration of provided resources.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers(GET,"/suche").permitAll()
                .antMatchers("/katalog").hasAuthority("ZUGRIFF_PRODUKT_KATALOG")
                .anyRequest().denyAll();
        // @formatter:on
    }

    /**
     * If no user details service is provided, spring will create a default one.
     * This default one will be configured to provide a initial default user with a
     * generated password. The generated password is also printed in log. To disable
     * this default behaviour, an in-memory service without any user is provided. It
     * won't be used anyway, since user details are request by resource server from
     * configured authorization server.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

}
