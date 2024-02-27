package com.sangkyun.VideoSNS.mapper;

import com.sangkyun.VideoSNS.dto.VideoDto;
import com.sangkyun.VideoSNS.model.Video;

public interface VideoMapper {
	
		VideoDto toVideoDto(Video video);
}
