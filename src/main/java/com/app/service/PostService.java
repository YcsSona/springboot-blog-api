package com.app.service;

import java.util.List;

import com.app.payload.PostDto;

public interface PostService {

	PostDto createPost(PostDto postDto);

	List<PostDto> getAllPosts();
}
