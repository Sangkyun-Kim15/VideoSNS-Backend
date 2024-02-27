package com.sangkyun.VideoSNS.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sangkyun.VideoSNS.dto.VideoDto;
import com.sangkyun.VideoSNS.model.Video;

@Service
public class VideoMapperImpl implements VideoMapper {

	@Override
	public VideoDto toVideoDto(Video video) {
		if (video == null) {
			return null;
		}
		// Ensure to convert video.getUser() to video.getUser().getId() to get the
		// userId
		return new VideoDto(video.getVideoId(), video.getUser().getId(), video.getTitle(), video.getDescription(),
				video.getFilename(), video.getFilePath(), Integer.parseInt(video.getDuration()),
				Long.parseLong(video.getSize()), video.getStatus(), LocalDateTime.parse(video.getCreatedAt()),
				LocalDateTime.parse(video.getUpdatedAt()));
	}
}