package com.system.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.blog.model.BlogUser;

public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {

	public Optional<BlogUser> findByEmail(String email);

	public Optional<BlogUser> findByUsernameOrEmail(String username, String email);

	public Optional<BlogUser> findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);
}
