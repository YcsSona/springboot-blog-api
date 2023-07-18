package com.app.service;

import com.app.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
}
