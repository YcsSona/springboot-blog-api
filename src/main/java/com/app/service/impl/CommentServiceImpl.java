package com.app.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Comment;
import com.app.entity.Post;
import com.app.exception.ResourceNotFoundException;
import com.app.payload.CommentDto;
import com.app.repository.CommentRepository;
import com.app.repository.PostRepository;
import com.app.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// set post to comment entity
		comment.setPost(post);

		Comment newComment = commentRepository.save(comment);

		return mapToDto(newComment);
	}

	private CommentDto mapToDto(Comment comment) {
		return new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody());
	}

	private Comment mapToEntity(CommentDto commentDto) {
		return new Comment(commentDto.getName(), commentDto.getEmail(), commentDto.getBody());
	}

}
