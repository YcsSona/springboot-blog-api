package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
