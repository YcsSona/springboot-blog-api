package com.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Post;
import com.app.exception.ResourceNotFoundException;
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
		Post post = mapToEntity(postDto);

		// TODO save the post entity
		Post newPost = postRepository.save(post);

		// TODO Convert entity into dto
		PostDto postResponse = mapToDto(newPost);

		return postResponse;
	}

	@Override
	public List<PostDto> getAllPosts() {

		List<Post> posts = postRepository.findAll();

//		return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		return posts.stream().collect(Collectors.mapping(post -> mapToDto(post), Collectors.toList()));
	}

	@Override
	public PostDto getPostById(long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	// TODO Add a utility method to convert post entity to post dto
	private PostDto mapToDto(Post post) {
		return new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getContent());
	}

	// TODO Add a utility method to convert post entity to post dto
	private Post mapToEntity(PostDto postDto) {
		return new Post(postDto.getTitle(), postDto.getDescription(), postDto.getContent());
	}

}
