package com.ekta.BlogPostApp.service;

import com.ekta.BlogPostApp.exception.BadRequestException;
import com.ekta.BlogPostApp.exception.ResourceNotFoundException;
import com.ekta.BlogPostApp.models.Posts;
import com.ekta.BlogPostApp.models.Users;
import com.ekta.BlogPostApp.payload.PagedResponse;
import com.ekta.BlogPostApp.payload.PostRequest;
import com.ekta.BlogPostApp.payload.PostResponse;
import com.ekta.BlogPostApp.repository.PostRepository;
import com.ekta.BlogPostApp.repository.UserRepository;
import com.ekta.BlogPostApp.security.UserPrincipal;
import com.ekta.BlogPostApp.util.AppConstants;
import com.ekta.BlogPostApp.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public PagedResponse<PostResponse> getAllPosts(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Posts
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Posts> posts = postRepository.findAll(pageable);

        if(posts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), posts.getNumber(),
                    posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
        }

        // Map Posts to PostResponses containing post creator details
        List<Long> postIds = posts.map(Posts::getPostId).getContent();
        Map<Long, Users> creatorMap = getPostsCreatorMap(posts.getContent());

        List<PostResponse> postResponses = posts.map(post -> {
            return ModelMapper.mapPostToPostResponse(post,
                    creatorMap.get(post.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(postResponses, posts.getNumber(),
                posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
    }

    public PagedResponse<PostResponse> getPostsCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all polls created by the given username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Posts> posts = postRepository.findByCreatedBy(user.getId(), pageable);

        if (posts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), posts.getNumber(),
                    posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
        }

        // Map Postss to PostResponses containing vote counts and poll creator details
        List<Long> postIds = posts.map(Posts::getPostId).getContent();

        List<PostResponse> postResponses = posts.map(poll -> {
            return ModelMapper.mapPostToPostResponse(poll,
                    user);
        }).getContent();

        return new PagedResponse<>(postResponses, posts.getNumber(),
                posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
    }


    public Posts createPosts(PostRequest postRequest) {
        Posts post = new Posts();

        return postRepository.save(post);
    }

    public PostResponse getPostsById(Long postId, UserPrincipal currentUser) {
        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new ResourceNotFoundException("Posts", "id", postId));

        // Retrieve poll creator details
        Users creator = userRepository.findById(post.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", post.getCreatedBy()));

        return ModelMapper.mapPostToPostResponse(post, creator);
    }


    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    Map<Long, Users> getPostsCreatorMap(List<Posts> posts) {
        // Get Posts Creator details of the given list of posts
        List<Long> creatorIds = posts.stream()
                .map(Posts::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<Users> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, Users> creatorMap = creators.stream()
                .collect(Collectors.toMap(Users::getId, Function.identity()));

        return creatorMap;
    }
}
