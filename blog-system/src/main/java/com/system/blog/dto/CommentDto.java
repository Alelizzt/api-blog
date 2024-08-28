package com.system.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentDto {

	private long id;
	
	@NotEmpty(message = "The comment name must not be empty or null")
	private String name;
	
	@NotEmpty(message = "The email must not be empty or null")
	@Email
	private String email;
	
	@NotEmpty(message = "The comment body must not be empty or null")
	@Size(min = 10, message = "Comment body should be at least 10 characters")
	private String body;

	public CommentDto() {
		super();
	}
	
	

	public CommentDto(long id, String name, String email, String body) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.body = body;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
