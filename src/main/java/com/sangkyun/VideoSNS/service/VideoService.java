package com.sangkyun.VideoSNS.service;

import java.util.List;
import java.util.Optional;

import com.sangkyun.VideoSNS.model.Video;

public interface VideoService {
	
	Video registerVideo(Video video);
	
	Optional<Video> getVideoById(Long videoId);
	
	List<Video> getAllVideos();
	
	List<Video> getVideosByUserId(Long userId);
	
	Video updateVideo(Video video);

    void deleteVideo(Long videoId);
}
