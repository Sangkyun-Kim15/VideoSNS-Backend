package com.sangkyun.VideoSNS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangkyun.VideoSNS.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{

}
