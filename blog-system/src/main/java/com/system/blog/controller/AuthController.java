package com.system.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.blog.dto.LoginDto;
import com.system.blog.dto.RegisterDto;
import com.system.blog.model.BlogUser;
import com.system.blog.model.Role;
import com.system.blog.repository.BlogUserRepository;
import com.system.blog.repository.RoleRepository;
import com.system.blog.security.JWTAuthResponseDTO;
import com.system.blog.security.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Controlador para la autenticación de usuarios del blog
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Administración de la autenticación con roles y jwt")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BlogUserRepository blogUserRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Operation(summary = "Autenticación o login del usuario, genera un token al comprobar la existencia del usuario")
	@ApiResponse(responseCode = "200", description = "Usuario autenticado y token generado acorde")
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Get token from jwtTokenProvider
		String token = jwtTokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTAuthResponseDTO(token));
	}
	
	@Operation(summary = "Registro de usuario en la aplicación")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se registra el usuario", content = @Content(schema = @Schema(implementation = RegisterDto.class))),
			@ApiResponse(responseCode = "400", description = "La petición se realizón de forma inadecuada y no pasó las validaciones")
	})
	@PostMapping("/signup")
	public ResponseEntity<?> userSignUp(@RequestBody RegisterDto registerDto) {
		if(blogUserRepository.existsByUsername(registerDto.getUsername())) {
			return new ResponseEntity<>("Already registered user with that username!", HttpStatus.BAD_REQUEST);
		}
		if(blogUserRepository.existsByEmail(registerDto.getEmail())) {
			return new ResponseEntity<>("Already registered user with that email!", HttpStatus.BAD_REQUEST);
		}
		
		BlogUser user = new BlogUser();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));
		
		blogUserRepository.save(user);
		return new ResponseEntity<>("Succesfully registered user!", HttpStatus.OK);
		
	}
}
