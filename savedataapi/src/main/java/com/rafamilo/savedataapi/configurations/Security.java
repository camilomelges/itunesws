package com.rafamilo.savedataapi.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Value("${savedataapi.properties.cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Value("${savedataapi.properties.cors.allowedMethods}")
    private String[] allowedMethods;

    @Value("${savedataapi.properties.cors.allowedHeaders}")
    private String allowedHeaders;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
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
