package com.ekta.BlogPostApp.repository;

import com.ekta.BlogPostApp.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Integer> {
}
