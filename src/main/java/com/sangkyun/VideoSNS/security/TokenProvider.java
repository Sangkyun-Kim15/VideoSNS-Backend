package com.sangkyun.VideoSNS.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


//Marks the class for SLF4J logging support and as a Spring-managed component, allowing it to be autodetected through classpath scanning.
@Slf4j // Lombok annotation that provides a logger.
@Component // Indicates that this is a Spring component.
public class TokenProvider {

	// Fields injected with values from the application's properties, used for configuring the JWT.
    @Value("${app.jwt.secret}") // The secret key used for signing JWTs.
    private String jwtSecret;
    
    @Value("${app.jwt.expiration.minutes}") // The expiration time of JWTs in minutes.
    private Long jwtExpirationMinutes;

    // Method for generating a JWT for an authenticated user.
    public String generate(Authentication authentication) {
    	// Casts the Authentication principal to the application-specific CustomUserDetails.
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        
        // Extracts the user's roles (authorities) into a List of Strings.
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Converts the JWT secret from String to a byte array for the signing key.
        byte[] signingKey = jwtSecret.getBytes();

        // Constructs the JWT with specified claims and signs it.
        return Jwts.builder()
                .header().add("typ", TOKEN_TYPE) // Sets the token type in the header.
                .and()
                .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512) // Signs the JWT with the HS512 algorithm.
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(jwtExpirationMinutes).toInstant())) // Sets the token's expiration time.
                .issuedAt(Date.from(ZonedDateTime.now().toInstant())) // Sets the token's issued at time.
                .id(UUID.randomUUID().toString()) // Sets a unique identifier for the token.
                .issuer(TOKEN_ISSUER) // Sets the token's issuer.
                .audience().add(TOKEN_AUDIENCE) // Sets the token's audience.
                .and()
                .subject(user.getUsername()) // Sets the subject of the token (typically the user's username).
                .claim("rol", roles) // Adds a custom claim for the user's roles.
                .claim("name", user.getName())
                .claim("preferred_username", user.getUsername())
                .claim("email", user.getEmail())
                .compact(); // Builds the JWT and serializes it to a compact, URL-safe string.
    }

    // Method for validating a JWT and extracting its claims.
    public Optional<Jws<Claims>> validateTokenAndGetJws(String token) {
        try {
            byte[] signingKey = jwtSecret.getBytes();
            // Parses the token, validates its signature, and returns its claims if valid.
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(signingKey))
                    .build()
                    .parseSignedClaims(token);

            return Optional.of(jws);
        } catch (ExpiredJwtException exception) {
            log.error("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (SignatureException exception) {
            log.error("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }
        return Optional.empty(); // Returns an empty Optional if the token is invalid for any reason.
    }
    
    // Constants defining the token's type, issuer, and audience.
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "order-api";
    public static final String TOKEN_AUDIENCE = "order-app";
}
