package com.system.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	public List<Comment> findByPostId(long postId);
}
