package com.sangkyun.VideoSNS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangkyun.VideoSNS.model.User;
import com.sangkyun.VideoSNS.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{
	List<Video> findByUserId(Long userId);
}
