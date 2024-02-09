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
@Table(name = "video_metadata")
public class VideoMetadata {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long metadataId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "video_id", nullable = false)
	private Video video;

	private String key;
	private String value;

	public VideoMetadata(Long metadataId, Video video, String key, String value) {
		super();
		this.metadataId = metadataId;
		this.video = video;
		this.key = key;
		this.value = value;
	}
}
