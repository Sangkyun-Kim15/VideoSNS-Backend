package com.sangkyun.VideoSNS.mapper;

import org.springframework.stereotype.Service;

import com.sangkyun.VideoSNS.dto.UserDto;
import com.sangkyun.VideoSNS.model.User;

@Service
public class UserMapperImpl implements UserMapper {

	@Override
	public UserDto toUserDto(User user) {
		if (user == null) {
			return null;
		}
		return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getRole());
	}
}
