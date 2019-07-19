package com.ekta.BlogPostApp.resource;

import com.ekta.BlogPostApp.models.Posts;
import com.ekta.BlogPostApp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public List<Posts> alternate() {
        return postRepository.findAll();
    }

    @PostMapping("/addPost")
    public List<Posts> addPost(@RequestBody final Posts post){
        postRepository.save(post);
        return postRepository.findAll();
    }

}