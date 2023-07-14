package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payload.PostDto;
import com.app.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	// TODO Add a request handling method to create a new post
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getAllPosts() {
		return ResponseEntity.ok(postService.getAllPosts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPostById(@PathVariable long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}
}
