package com.system.blog.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PostDto {

	private Long id;

	@NotEmpty
	@Size(min = 2, message = "Post title should be at least 2 characters")
	private String title;

	@NotEmpty
	@Size(min = 10, message = "Post description should be at least 10 characters")
	private String description;

	@NotEmpty
	private String content;

	private Set<CommentDto> comments;

	public PostDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<CommentDto> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}

}
