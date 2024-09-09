package com.system.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.blog.dto.PostDto;
import com.system.blog.dto.PostResponse;
import com.system.blog.service.PostService;
import com.system.blog.util.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Administración de las publicaciones en el blog")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping
	@Operation(summary = "Obtiene las publicaciones guardadas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publicaciones encontradas",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class))))
	})
	public PostResponse getPosts(
			@Parameter(description = "Número de página actual")
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
			@Parameter(description = "Tamaño de páginas a visualizar")
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@Parameter(description = "Ordena según el parámetro utilizado")
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@Parameter(description = "Ordena de forma descendente o ascendente", example = "desc")
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtiene las publicaciones segun el identificador proporcionado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publicación encontrada",
					content = @Content(schema = @Schema(implementation = PostDto.class)))
	})
	public ResponseEntity<PostDto> getPostById(
			@Parameter(description = "Identificador de la publicación")
			@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@Operation(summary = "Guarda la publicación")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Publicación almacenada con éxito",
					content = @Content(schema = @Schema(implementation = PostDto.class)))
	})
	public ResponseEntity<PostDto> savePost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	@Operation(summary = "Actualiza la publicación")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publicación actualizada con éxito",
					content = @Content(schema = @Schema(implementation = PostDto.class)))
	})
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
		PostDto postResponse = postService.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@Operation(summary = "Elimina la publicación")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publicación eliminada con éxito")
	})
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
		postService.deletePost(id);
		return new ResponseEntity<>("Post deleted without any problems!", HttpStatus.OK);
	}

}
