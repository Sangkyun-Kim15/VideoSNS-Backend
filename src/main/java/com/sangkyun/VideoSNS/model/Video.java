package com.sangkyun.VideoSNS.model;

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
@Table(name = "videos")
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long videoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private String title;
	private String description;
	private String filename;
	private String filePath;
	private String duration;
	private String size;
	private String status;
	private String createdAt;
	private String updatedAt;

	public Video(Long videoId, User user, String title, String description, String filename, String filePath,
			String duration, String size, String status, String createdAt, String updatedAt) {
		super();
		this.videoId = videoId;
		this.user = user;
		this.title = title;
		this.description = description;
		this.filename = filename;
		this.filePath = filePath;
		this.duration = duration;
		this.size = size;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
