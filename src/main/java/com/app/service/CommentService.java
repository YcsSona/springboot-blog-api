package com.app.service;

import java.util.List;

import com.app.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);

	List<CommentDto> getCommentsByPostId(long postId);

	CommentDto getCommentById(long postId, long commendId);
}
