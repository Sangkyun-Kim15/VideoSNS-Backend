package com.sangkyun.VideoSNS.security.oauth2;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.sangkyun.VideoSNS.model.User;
import com.sangkyun.VideoSNS.security.CustomUserDetails;
import com.sangkyun.VideoSNS.security.WebSecurityConfig;
import com.sangkyun.VideoSNS.service.UserService;

import java.util.List;
import java.util.Optional;

//Marks this class as a Spring component, making it a candidate for Spring's component scanning to detect and register as a bean.
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

 // Dependency injection of the UserService to interact with the application's users.
	private final UserService userService;
 // A list of OAuth2UserInfoExtractor instances, allowing for customization based on different OAuth2 providers.
	private final List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors;

 // Constructor for dependency injection.
	public CustomOAuth2UserService(UserService userService, List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors) {
		this.userService = userService;
		this.oAuth2UserInfoExtractors = oAuth2UserInfoExtractors;
	}

 // Overrides the loadUser method to customize the OAuth2 user loading process.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
     // Calls the superclass method to load the OAuth2User from the external OAuth2 provider.
		OAuth2User oAuth2User = super.loadUser(userRequest);

     // Determines the appropriate OAuth2UserInfoExtractor for the current OAuth2 provider.
		Optional<OAuth2UserInfoExtractor> oAuth2UserInfoExtractorOptional = oAuth2UserInfoExtractors.stream()
				.filter(oAuth2UserInfoExtractor -> oAuth2UserInfoExtractor.accepts(userRequest)).findFirst();
     // Throws an exception if no suitable extractor is found, indicating the provider is not supported.
		if (oAuth2UserInfoExtractorOptional.isEmpty()) {
			throw new InternalAuthenticationServiceException("The OAuth2 provider is not supported yet");
		}

     // Extracts custom user details from the OAuth2User information.
		CustomUserDetails customUserDetails = oAuth2UserInfoExtractorOptional.get().extractUserInfo(oAuth2User);
     // Upserts (updates or inserts) the user in the application's database.
		User user = upsertUser(customUserDetails);
     // Updates the customUserDetails with the user's ID from the database.
		customUserDetails.setId(user.getId());
     // Returns the CustomUserDetails, integrating OAuth2 authentication with the application's security.
		return customUserDetails;
	}

 // A method to update an existing user or insert a new user based on the OAuth2 authentication result.
	private User upsertUser(CustomUserDetails customUserDetails) {
     // Checks if the user already exists in the application's database.
		Optional<User> userOptional = userService.getUserByUsername(customUserDetails.getUsername());
		User user;
		if (userOptional.isEmpty()) {
         // If the user does not exist, creates a new user with the details from OAuth2 authentication.
			user = new User();
			user.setUsername(customUserDetails.getUsername());
			user.setName(customUserDetails.getName());
			user.setEmail(customUserDetails.getEmail());
			user.setImageUrl(customUserDetails.getAvatarUrl());
			user.setProvider(customUserDetails.getProvider());
         // Assigns a default role to the new user.
			user.setRole(WebSecurityConfig.USER);
		} else {
         // If the user exists, updates their details.
			user = userOptional.get();
			user.setEmail(customUserDetails.getEmail());
			user.setImageUrl(customUserDetails.getAvatarUrl());
		}
     // Saves the user to the database and returns the updated or new user.
		return userService.saveUser(user);
	}
}