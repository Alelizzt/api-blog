package com.system.blog.service;

import com.system.blog.dto.PostDto;
import com.system.blog.dto.PostResponse;

public interface PostService {

	public PostDto createPost(PostDto postDto);

	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);

	public PostDto getPostById(long id);

	public PostDto updatePost(PostDto postDto, long id);

	public void deletePost(long id);
}
