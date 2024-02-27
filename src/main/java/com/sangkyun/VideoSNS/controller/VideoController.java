package com.sangkyun.VideoSNS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangkyun.VideoSNS.mapper.VideoMapper;
import com.sangkyun.VideoSNS.service.VideoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/videos")
public class VideoController {

	private final VideoService videoService;
	private final VideoMapper videoMapper;
}
