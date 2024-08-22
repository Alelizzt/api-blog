package com.system.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.system.blog.dto.PostDto;
import com.system.blog.dto.PostResponse;
import com.system.blog.exception.ResourceNotFoundException;
import com.system.blog.model.Post;
import com.system.blog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public PostDto createPost(PostDto postDto) {
		Post post = mapEntity(postDto);
		Post newPost = postRepository.save(post);

		PostDto postResponse = mapDto(newPost);

		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> posts = postRepository.findAll(pageable);
		
		List<Post> postsList = posts.getContent();
		List<PostDto> content = postsList.stream().map(post -> mapDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
		
	}

	// Convierte la entidad a DTO
	private PostDto mapDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());

		return postDto;
	}

	// Convierte el DTO a Entidad
	private Post mapEntity(PostDto postDto) {
		Post post = new Post();
		post.setId(postDto.getId());
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		return post;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		Post postUpdated = postRepository.save(post);
		return mapDto(postUpdated);
	}

	@Override
	public void deletePost(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		postRepository.delete(post);
	}

}
