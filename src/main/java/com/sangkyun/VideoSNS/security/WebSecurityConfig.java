package com.sangkyun.VideoSNS.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sangkyun.VideoSNS.security.oauth2.CustomAuthenticationSuccessHandler;
import com.sangkyun.VideoSNS.security.oauth2.CustomOAuth2UserService;

//Marks this class as a configuration class and enables Spring Security for the application.
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required arguments for final fields.
@Configuration // Indicates that this class is a configuration class.
@EnableWebSecurity // Enables Spring Security's web security support and provides Spring MVC integration.
public class WebSecurityConfig {
 // Autowired dependencies for custom OAuth2 and authentication handling.
 private final CustomOAuth2UserService customOauth2UserService; // Service to load user details from OAuth2 services.
 private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler; // Handler for successful authentication.
 private final TokenAuthenticationFilter tokenAuthenticationFilter; // Filter to authenticate users based on a token.

 // Bean to expose the AuthenticationManager from the AuthenticationConfiguration.
 @Bean
 AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
         throws Exception {
     return authenticationConfiguration.getAuthenticationManager(); // Obtains the AuthenticationManager to manage authentication within the app.
 }

 // Security filter chain to configure HTTP security, specifying which endpoints are secured and how.
 @Bean
 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     return http
             // Authorization rules for HTTP requests.
             .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                     // Specifies access rules for various API endpoints.
                     .requestMatchers(HttpMethod.GET, "/api/movies", "/api/movies/**").hasAnyAuthority(ADMIN, USER) // GET requests to movies are allowed for ADMIN and USER roles.
                     .requestMatchers(HttpMethod.GET, "/api/users/me").hasAnyAuthority(ADMIN, USER) // Allows users to access their own data.
                     .requestMatchers("/api/movies", "/api/movies/**").hasAnyAuthority(ADMIN) // Restricts POST, DELETE, and PUT on movies to ADMIN.
                     .requestMatchers("/api/users", "/api/users/**").hasAnyAuthority(ADMIN) // Allows only ADMIN to access user management endpoints.
                     .requestMatchers("/public/**", "/auth/**", "/oauth2/**").permitAll() // Publicly accessible endpoints.
                     .requestMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**")
                     .permitAll().anyRequest().authenticated()) // Any other request must be authenticated.
             // Configuration for OAuth2 login.
             .oauth2Login(oauth2Login -> oauth2Login.userInfoEndpoint().userService(customOauth2UserService)
                     .and().successHandler(customAuthenticationSuccessHandler)) // Configures OAuth2 login with custom user service and success handler.
             .logout(l -> l.logoutSuccessUrl("/").permitAll()) // Configures logout behavior.
             .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Adds a custom filter for token-based authentication.
             .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configures session management to be stateless.
             .exceptionHandling(exceptionHandling -> exceptionHandling
                     .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))) // Handles exceptions for unauthorized access.
             .cors(Customizer.withDefaults()) // Enables CORS with default settings.
             .csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection for APIs (common in REST APIs).
             .build(); // Builds the security filter chain.
 }

 // Bean to encode passwords using BCrypt hashing algorithm.
 @Bean
 PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder(); // Specifies the use of BCrypt for password encoding.
 }

 // Constants to represent ADMIN and USER roles within the application.
 public static final String ADMIN = "ADMIN";
 public static final String USER = "USER";
}
