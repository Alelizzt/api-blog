package com.system.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.blog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
