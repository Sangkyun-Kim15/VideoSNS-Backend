package com.sangkyun.VideoSNS.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//Indicates that this class is a source of bean definitions for the application context.
@Configuration
public class CorsConfig {

    // Defines a bean that will be managed by the Spring container. When Spring sees
    // @Bean,
    // it will execute the method and manage the returned value as a bean within the
    // ApplicationContext.
    @Bean
    CorsConfigurationSource corsConfigurationSource(
            // Injects a value from the application properties file into the allowedOrigins
            // list.
            // The value comes from the property "app.cors.allowed-origins".
            @Value("${app.cors.allowed-origins}") List<String> allowedOrigins) {
		// Creates a new instance of CorsConfiguration which will be configured and
		// returned.
		CorsConfiguration configuration = new CorsConfiguration();
		// Allows cookies and other credentials to be included in the request.
		configuration.setAllowCredentials(true);
		// Sets the list of origins that are allowed to access the resources.
		configuration.setAllowedOrigins(allowedOrigins);
		// Allows all HTTP methods (e.g., GET, POST) from the origins specified.
		configuration.addAllowedMethod("*");
		// Allows all headers in requests from the specified origins.
		configuration.addAllowedHeader("*");
		// Prepares a URL-based CORS configuration source.
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// Registers the CorsConfiguration for all paths (/**) in the application.
		source.registerCorsConfiguration("/**", configuration);
		// Returns the configured CORS source which will be used by Spring Security.
		return source;
	}
}
