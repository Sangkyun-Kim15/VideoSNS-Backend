package com.sangkyun.VideoSNS.security.oauth2;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sangkyun.VideoSNS.security.CustomUserDetails;
import com.sangkyun.VideoSNS.security.WebSecurityConfig;

import java.util.Collections;

//Marks the class as a Spring-managed service component, making it a candidate for Spring's component scanning and auto-detection.
@Service
public class GoogleOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

 // Overrides the extractUserInfo method to define how user information from Google's OAuth2 authentication is processed.
	@Override
	public CustomUserDetails extractUserInfo(OAuth2User oAuth2User) {
	    // Creates a new instance of CustomUserDetails to populate with user information.
		CustomUserDetails customUserDetails = new CustomUserDetails();
		
		// Populates customUserDetails with information retrieved from the OAuth2User object.
		// Uses a helper method to safely retrieve attributes from the OAuth2User's attribute map.
		customUserDetails.setUsername(retrieveAttr("email", oAuth2User)); // Sets the username to the user's email.
		customUserDetails.setName(retrieveAttr("name", oAuth2User)); // Sets the name.
		customUserDetails.setEmail(retrieveAttr("email", oAuth2User)); // Sets the email.
		customUserDetails.setAvatarUrl(retrieveAttr("picture", oAuth2User)); // Sets the avatar URL.
		customUserDetails.setProvider(OAuth2Provider.GOOGLE); // Identifies the OAuth2 provider as Google.
		customUserDetails.setAttributes(oAuth2User.getAttributes()); // Stores all OAuth2 attributes.
		// Assigns a default authority of USER to the authenticated OAuth2 user.
		customUserDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority(WebSecurityConfig.USER)));
		return customUserDetails; // Returns the populated CustomUserDetails object.
	}

 // Overrides the accepts method to indicate that this extractor handles OAuth2UserRequests for Google.
	@Override
	public boolean accepts(OAuth2UserRequest userRequest) {
	    // Checks if the OAuth2UserRequest's client registration matches Google's registration ID.
		return OAuth2Provider.GOOGLE.name().equalsIgnoreCase(userRequest.getClientRegistration().getRegistrationId());
	}

 // Helper method to safely retrieve attributes from the OAuth2User object, preventing null values.
	private String retrieveAttr(String attr, OAuth2User oAuth2User) {
	    // Retrieves the attribute from the OAuth2User's attributes map.
		Object attribute = oAuth2User.getAttributes().get(attr);
		// Returns the attribute as a string, or an empty string if the attribute is null.
		return attribute == null ? "" : attribute.toString();
	}
}