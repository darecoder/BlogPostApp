package com.ekta.BlogPostApp.repository;

import com.ekta.BlogPostApp.models.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {
    Optional<Posts> findByPostId(Long postId);

    Page<Posts> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Posts> findByPostIdIn(List<Long> postIds);

    List<Posts> findByPostIdIn(List<Long> postIds, Sort sort);
}
