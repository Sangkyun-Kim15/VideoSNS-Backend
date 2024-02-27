package com.sangkyun.VideoSNS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sangkyun.VideoSNS.exception.VideoNotFoundException;
import com.sangkyun.VideoSNS.model.Video;
import com.sangkyun.VideoSNS.repository.VideoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VideoServiceImpl implements VideoService {
	
	private final VideoRepository videoRepository;

	@Override
	public Video registerVideo(Video video) {
		return videoRepository.save(video);
	}

	@Override
	public Optional<Video> getVideoById(Long videoId) {
		return videoRepository.findById(videoId);
	}

	@Override
	public List<Video> getAllVideos() {
		return videoRepository.findAll();
	}

	@Override
	public List<Video> getVideosByUserId(Long userId) {
		return videoRepository.findByUserId(userId);
	}

	@Override
	public Video updateVideo(Video video) {
		return videoRepository.save(video);
	}

	@Override
	public void deleteVideo(Long videoId) {
		Video video = videoRepository.findById(videoId).orElseThrow(
                () -> new VideoNotFoundException("Video with id " + videoId + " not found"));
        videoRepository.delete(video);
	}

}
