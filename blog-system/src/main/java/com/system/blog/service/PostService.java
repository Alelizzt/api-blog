package com.system.blog.service;

import java.util.List;

import com.system.blog.dto.PostDto;

public interface PostService {

	public PostDto createPost(PostDto postDto);
	
	public List<PostDto> getAllPosts();
	
	public PostDto getPostById(long id);
	
	public PostDto updatePost(PostDto postDto, long id);
	
	public void deletePost(long id);
}
