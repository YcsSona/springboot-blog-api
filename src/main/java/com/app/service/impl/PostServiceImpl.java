package com.app.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Post;
import com.app.payload.PostDto;
import com.app.repository.PostRepository;
import com.app.service.PostService;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public PostDto createPost(PostDto postDto) {
		// TODO Convert dto to entity
		Post post = new Post(postDto.getTitle(), postDto.getDescription(), postDto.getContent());

		// TODO save the post entity
		Post newPost = postRepository.save(post);

		// TODO Convert entity into dto
		PostDto postResponse = new PostDto(newPost.getId(), newPost.getTitle(), newPost.getDescription(),
				newPost.getContent());
		
		return postResponse;
	}

}
