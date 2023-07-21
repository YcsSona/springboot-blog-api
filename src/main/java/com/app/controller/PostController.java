package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.payload.PostDto;
import com.app.service.PostService;
import com.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	// TODO Add a request handling method to create a new post
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody @Valid PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPostById(@PathVariable long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePost(@RequestBody @Valid PostDto postDto, @PathVariable long id) {
		return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable long id) {
		postService.deletePostById(id);
		return new ResponseEntity<>("Post with ID : " + id + " deleted successfully", HttpStatus.OK);
	}
}
