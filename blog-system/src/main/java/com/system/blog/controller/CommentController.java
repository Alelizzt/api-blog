package com.system.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.blog.dto.CommentDto;
import com.system.blog.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Comments", description = "Administración de los comentarios en los posts")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping("/posts/{postId}/comments")
	@Operation(summary = "Obtiene los comentarios de un post según su identificador")
	public List<CommentDto> getCommentsByPost(@PathVariable(value = "postId") Long postId) {
		return commentService.getCommentsByPostId(postId);
	}

	@GetMapping("/posts/{postId}/comments/{commentId}")
	@Operation(summary = "Obtiene un comentario según su identificador y el del post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Comentario encontrado",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDto.class))))
	})
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId) {
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}

	@PostMapping("/posts/{postId}/comments")
	@Operation(summary = "Guarda un comentario según el identificador del post al cual pertenece")
	@ApiResponse(responseCode = "200", description = "Se registra el nuevo comentario", content = @Content(schema = @Schema(implementation = CommentDto.class)))
	public ResponseEntity<CommentDto> saveComment(@PathVariable(value = "postId") long postId,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	@Operation(summary = "Actualiza un comentario según el identificador del post al cual pertenece")
	@ApiResponse(responseCode = "200", description = "Se ha actualizado correctamente el comentario")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId, @Valid @RequestBody CommentDto commentDto) {
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	@Operation(summary = "Elimina el comentario según el identificador del post al cual pertenece")
	@ApiResponse(responseCode = "200", description = "Se ha eliminado correctamente el comentario")
	public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Deleted comment", HttpStatus.OK);
	}
}
