package com.sangkyun.VideoSNS.mapper;

import com.sangkyun.VideoSNS.dto.UserDto;
import com.sangkyun.VideoSNS.model.User;

public interface UserMapper {

	UserDto toUserDto(User user);
}