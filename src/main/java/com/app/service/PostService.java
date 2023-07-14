package com.app.service;

import com.app.payload.PostDto;
import com.app.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);

	PostDto getPostById(long id);

	void deletePostById(long id);

	PostDto updatePost(PostDto postDto, long id);
}
