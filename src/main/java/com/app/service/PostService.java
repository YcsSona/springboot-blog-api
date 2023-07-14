package com.app.service;

import java.util.List;

import com.app.payload.PostDto;

public interface PostService {

	PostDto createOrUpdatePost(PostDto postDto);

	List<PostDto> getAllPosts();

	PostDto getPostById(long id);
}
