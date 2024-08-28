package com.system.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.blog.model.Role;
import com.system.blog.model.BlogUser;

public interface RoleRepository extends JpaRepository<BlogUser, Long> {

	public Optional<Role> findByName(String name);
}
