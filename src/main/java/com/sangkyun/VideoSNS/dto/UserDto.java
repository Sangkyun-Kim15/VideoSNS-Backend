package com.sangkyun.VideoSNS.dto;

/*
  "record"
  
  This keyword is used to define a record in Java. 
  
  Records are a form of classes that automatically implement equals(), hashCode(), and toString() methods
  
  based on the fields defined in the record's constructor. 
  
  Records aim to reduce the boilerplate code needed for data-carrying classes.
 */
public record UserDto(Long id, String username, String name, String email, String role) {
}
