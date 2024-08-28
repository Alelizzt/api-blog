package com.system.blog.dto;

import java.util.Set;

import com.system.blog.model.Comment;

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
	
	private Set<Comment> comments;

	public PostDto() {
		super();
	}

	public PostDto(long id, String title, String description, String content) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}
