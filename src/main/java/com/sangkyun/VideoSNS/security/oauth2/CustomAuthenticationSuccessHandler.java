package com.sangkyun.VideoSNS.security.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.sangkyun.VideoSNS.security.TokenProvider;

import java.io.IOException;

//Marks this class as a component managed by Spring, making it eligible for component scanning and auto-detection.
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required arguments for final field(s).
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

 // Injects the TokenProvider bean used for generating authentication tokens.
	private final TokenProvider tokenProvider;

 // Injects the application property 'app.oauth2.redirectUri' to determine where users should be redirected after successful authentication.
	@Value("${app.oauth2.redirectUri}")
	private String redirectUri;

 // Overrides the onAuthenticationSuccess method from SimpleUrlAuthenticationSuccessHandler.
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
	    // Handles the successful authentication by performing the custom redirection logic.
		handle(request, response, authentication);
		// Clears authentication attributes from the session.
		super.clearAuthenticationAttributes(request);
	}

 // Customizes the redirection logic after a successful authentication.
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
	    // Determines the target URL for redirection. Uses the configured redirectUri or defaults to the URL determined by Spring Security.
		String targetUrl = redirectUri.isEmpty() ? determineTargetUrl(request, response, authentication) : redirectUri;

		// Generates an authentication token for the authenticated user.
		String token = tokenProvider.generate(authentication);
		// Appends the token as a query parameter to the target URL.
		targetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString();
		// Redirects the user to the target URL with the token.
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}

