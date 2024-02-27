package com.sangkyun.VideoSNS.dto;

import java.time.LocalDateTime;

public record VideoDto(Long videoId,
        Long userId, // Assuming you want to keep track of which user uploaded the video
        String title,
        String description,
        String filename,
        String filePath,
        Integer duration,
        Long size,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
