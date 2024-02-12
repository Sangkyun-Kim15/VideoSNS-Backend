package com.sangkyun.VideoSNS.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "video_views")
public class VideoViews {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long viewId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "video_id", nullable = false)
	private Video video;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	private LocalDateTime viewedAt;

	public VideoViews(Long viewId, Video video, User user, LocalDateTime viewedAt) {
		super();
		this.viewId = viewId;
		this.video = video;
		this.user = user;
		this.viewedAt = viewedAt;
	}
}
		