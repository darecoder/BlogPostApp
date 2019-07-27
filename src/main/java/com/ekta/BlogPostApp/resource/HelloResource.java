package com.ekta.BlogPostApp.resource;

import com.ekta.BlogPostApp.exception.ResourceNotFoundException;
import com.ekta.BlogPostApp.models.Posts;
import com.ekta.BlogPostApp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RequestMapping("/")
@RestController
public class HelloResource {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/")
    public String hello() {
        return "Hello Everyone";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/**")
    public String securedHello() {
        return "Hey Admin";
    }

    @PreAuthorize("hasAnyRole('USER') || hasAnyRole('ADMIN')")
    @GetMapping("/secured/users")
    public String securedUsers() {
        return "Welcome Users";
    }

    @PreAuthorize("hasAnyRole('USER') || hasAnyRole('ADMIN')")
    @GetMapping("/secured/posts")
    public List<Posts> getPosts() {
        return postRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('USER') || hasAnyRole('ADMIN')")
    @PostMapping("secured/addpost")
    public List<Posts> addPost(@RequestBody final Posts post){
        postRepository.save(post);
        return postRepository.findAll();
    }

}