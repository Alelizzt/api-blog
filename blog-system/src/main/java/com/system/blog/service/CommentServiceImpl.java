package com.system.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.system.blog.dto.CommentDto;
import com.system.blog.exception.BlogAppException;
import com.system.blog.exception.ResourceNotFoundException;
import com.system.blog.model.Comment;
import com.system.blog.model.Post;
import com.system.blog.repository.CommentRepository;
import com.system.blog.repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapEntity(commentDto);
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		comment.setPost(post);
		Comment newComment = commentRepository.save(comment);
		return mapDto(newComment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		return comments.stream().map(comment -> mapDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "The comment doesn't belong to the post");
		}

		return mapDto(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequested) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "The comment doesn't belong to the post");
		}

		comment.setName(commentRequested.getName());
		comment.setEmail(commentRequested.getEmail());
		comment.setBody(commentRequested.getBody());

		Comment updatedComment = commentRepository.save(comment);
		return mapDto(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "The comment doesn't belong to the post");
		}

		commentRepository.delete(comment);

	}

	private CommentDto mapDto(Comment comment) {
		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		return commentDto;
	}

	private Comment mapEntity(CommentDto commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);
		return comment;
	}

}
