package com.ekta.BlogPostApp.controller;

import com.ekta.BlogPostApp.models.Posts;
import com.ekta.BlogPostApp.payload.ApiResponse;
import com.ekta.BlogPostApp.payload.PagedResponse;
import com.ekta.BlogPostApp.payload.PostRequest;
import com.ekta.BlogPostApp.payload.PostResponse;
import com.ekta.BlogPostApp.repository.PostRepository;
import com.ekta.BlogPostApp.repository.UserRepository;
import com.ekta.BlogPostApp.security.CurrentUser;
import com.ekta.BlogPostApp.security.UserPrincipal;
import com.ekta.BlogPostApp.service.PostService;
import com.ekta.BlogPostApp.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @GetMapping
    public PagedResponse<PostResponse> getPosts(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return postService.getAllPosts(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createPosts(@Valid @RequestBody PostRequest postRequest) {
        Posts post = postService.createPosts(postRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pollId}")
                .buildAndExpand(post.getPostId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Posts Created Successfully"));
    }

    @GetMapping("/{postId}")
    public PostResponse getPostsById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long pollId) {
        return postService.getPostsById(pollId, currentUser);
    }

}
