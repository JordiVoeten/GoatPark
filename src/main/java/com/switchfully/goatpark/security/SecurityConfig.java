package com.switchfully.goatpark.security;


import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.stream.Collectors;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Profile("!test")
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    /**
     * This is meant to add the role mapper to keycloak allowing you to map keycloak roles to your own features
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(collection ->
                collection.stream()
                        .map(keycloakRole -> Role.valueOf(keycloakRole.getAuthority().toUpperCase()))
                        .flatMap(role -> role.getFeatures().stream())
                        .map(feature -> new SimpleGrantedAuthority(feature.name()))
                        .collect(Collectors.toSet())
        );
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public org.keycloak.adapters.KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * Spring security configuration
     * This is the default security configuration and shouldn't be changed
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest()
                .authenticated().and().csrf().disable();
    }

    /**
     * Configure here the endpoints that need to be anonymously available
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/public-hello-world");
    }

    /**
     * This creates a client that allows you send requests to keycloak as an admin
     * <p>
     * TODO: Change the account used from master admin to admin of a specific client
     */
    @Bean
    public Keycloak keycloak(@Value("${master.keycloak.username}") String adminUsername, @Value("${master.keycloak.password}") String adminPassword) {
        return KeycloakBuilder.builder()
                .serverUrl("https://keycloak.switchfully.com/auth")
                .grantType(OAuth2Constants.PASSWORD)
                .realm("master")
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                )
                .build();
    }

}