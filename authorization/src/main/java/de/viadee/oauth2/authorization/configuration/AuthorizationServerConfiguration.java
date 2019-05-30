package de.viadee.oauth2.authorization.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Configuration of OAuth2 authorization server.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServerConfiguration(AuthenticationConfiguration authenticationConfiguration, PasswordEncoder passwordEncoder) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Security configuration of OAuth2 authorization server.
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        // @formatter:off
        oauthServer
            // Configure to use defined password encoder
            .passwordEncoder(passwordEncoder)
            // Limit access to /oauth/check_token to OAuth2 clients with special authorities
            .checkTokenAccess("hasAuthority('CHECK_AUTH_TOKEN')")
        ;
        // @formatter:on
    }


    /**
     * Configuration of OAuth2 authorization servers client source.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients
            // For this example, create client configuration in-memory
            .inMemory()
                .withClient("api-gateway")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("password")
                .scopes("default")
            .and()
                .withClient("produkte")
                .secret(passwordEncoder.encode("secret"))
                .authorities("CHECK_AUTH_TOKEN")
        ;
        // @formatter:on
    }

    /**
     * Configuration of OAuth2 authorization server endpoints.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // @formatter:off
        endpoints
            // Provide authentication manager to allow usage of password grant type.
            .authenticationManager(authenticationConfiguration.getAuthenticationManager())
            // Provide token store to remember issues access token
            .tokenStore(tokenStore())
        ;
        // @formatter:on
    }

    /**
     * For this simple example, provide in-memory token store to remember issues access tokens.
     */
    @Bean
    public InMemoryTokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

}
