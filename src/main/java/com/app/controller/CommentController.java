package com.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payload.CommentDto;
import com.app.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<?> createComment(@PathVariable long postId, @RequestBody @Valid CommentDto commentDto) {

		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}

	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<?> getCommentsByPostId(@PathVariable long postId) {
		return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
	}

	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<?> getCommentById(@PathVariable long postId, @PathVariable long commentId) {
		return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable long postId, @PathVariable long commentId,
			@RequestBody @Valid CommentDto commentDto) {
		return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable long postId, @PathVariable long commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment with ID : " + commentId + " deleted successfully", HttpStatus.OK);
	}
}
