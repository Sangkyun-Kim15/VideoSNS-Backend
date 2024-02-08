package com.sangkyun.VideoSNS.security.oauth2;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.sangkyun.VideoSNS.security.CustomUserDetails;

//Defines an interface for extracting custom user details from OAuth2User information.
public interface OAuth2UserInfoExtractor {

 // Method to extract custom user details from an OAuth2User object.
 // This method is intended to be implemented to parse the OAuth2User information (e.g., attributes) and map it to the application's CustomUserDetails object.
 CustomUserDetails extractUserInfo(OAuth2User oAuth2User);

 // Method to determine if the current extractor implementation should process the given OAuth2UserRequest.
 // This is useful for selecting the appropriate extractor based on the OAuth2 provider (e.g., Google, Facebook) or other criteria present in the OAuth2UserRequest.
 boolean accepts(OAuth2UserRequest userRequest);
}