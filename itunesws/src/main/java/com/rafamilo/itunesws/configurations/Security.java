package com.rafamilo.itunesws.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Value("${itunesws.properties.auth.basic.username}")
    private String username;

    @Value("${itunesws.properties.auth.basic.password}")
    private String password;

    @Value("${itunesws.properties.cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Value("${itunesws.properties.cors.allowedMethods}")
    private String[] allowedMethods;

    @Value("${itunesws.properties.cors.allowedHeaders}")
    private String allowedHeaders;

    @Value("${itunesws.properties.swagger.paths}")
    private String[] swaggerPaths;

    @Value("${itunesws.properties.swagger.enabled}")
    private Boolean swaggerEnabled;

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        final String BASIC_AUTH_ROLES = "APPLICATION";
        auth.inMemoryAuthentication().withUser(username).password("{noop}".concat(password)).roles(BASIC_AUTH_ROLES);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(swaggerEnabled ? swaggerPaths : new String[0])
                .permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and().httpBasic();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList(allowedMethods));
        configuration.addAllowedHeader(allowedHeaders);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final String REGISTER_CORS_CONFIGURATION_PATTERN = "/**";
        source.registerCorsConfiguration(REGISTER_CORS_CONFIGURATION_PATTERN, configuration);
        return source;
    }
}
