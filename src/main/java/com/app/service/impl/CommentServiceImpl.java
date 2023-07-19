package com.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.app.entity.Comment;
import com.app.entity.Post;
import com.app.exception.BlogAPIException;
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

	@Autowired
	private ModelMapper mapper;

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

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// retrive comments by post id

		List<Comment> posts = commentRepository.findByPostId(postId);

		// convert list of comment entities to list of commet dtos
		return posts.stream().collect(Collectors.mapping(p -> mapToDto(p), Collectors.toList()));
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {

		Comment comment = isCommentBelongsToPost(postId, commentId);

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		Comment comment = isCommentBelongsToPost(postId, commentId);

		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		// Added just to return the dto, otherwise not needed
		Comment updatedComment = commentRepository.save(comment);

		return mapToDto(updatedComment);
	}

	@Override
	public void deleteComment(long postId, long commentId) {

		Comment comment = isCommentBelongsToPost(postId, commentId);

		commentRepository.delete(comment);
	}

	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
		return comment;
	}

	private Comment isCommentBelongsToPost(long postId, long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		return comment;
	}

}
