package com.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.entity.Post;
import com.app.exception.ResourceNotFoundException;
import com.app.payload.PostDto;
import com.app.payload.PostResponse;
import com.app.repository.PostRepository;
import com.app.service.PostService;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public PostDto createPost(PostDto postDto) {
		// Convert dto to entity
		Post post = mapToEntity(postDto);

		// save the post entity
		Post newPost = postRepository.save(post);

		// Convert entity into dto
		PostDto postResponse = mapToDto(newPost);

		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		// Create pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Post> pages = postRepository.findAll(pageable);

		List<Post> posts = pages.getContent();

//		return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		List<PostDto> postDtos = posts.stream()
				.collect(Collectors.mapping(post -> mapToDto(post), Collectors.toList()));

		PostResponse response = new PostResponse(postDtos, pages.getNumber(), pages.getSize(), pages.getTotalElements(),
				pages.getTotalPages(), pages.isLast());

		return response;
	}

	@Override
	public PostDto getPostById(long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	@Override
	public void deletePostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		postRepository.delete(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		// Get the existing post by id
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		// update the necessary fields
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		// save the updated content in the DB
		Post updatedPost = postRepository.save(post);

		return mapToDto(updatedPost);
	}

	// Utility method to convert post entity to post dto
	private PostDto mapToDto(Post post) {
		PostDto postDto = mapper.map(post, PostDto.class);
		return postDto;
	}

	// Utility method to convert post entity to post dto
	private Post mapToEntity(PostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
		return post;
	}

}
