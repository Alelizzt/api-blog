package com.system.blog.service;

import java.util.List;

import com.system.blog.dto.CommentDto;

public interface CommentService {
	
	public CommentDto createComment(long postId, CommentDto commentDto);
	
	public List<CommentDto> getCommentsByPostId(long postId);
	
	public CommentDto getCommentById(Long postId, Long commentId);
	
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequested);
	
	public void deleteComment(Long postId, Long commentId);
}
