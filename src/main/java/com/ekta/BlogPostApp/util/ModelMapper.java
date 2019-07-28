package com.ekta.BlogPostApp.util;

import com.ekta.BlogPostApp.models.Posts;
import com.ekta.BlogPostApp.models.Users;
import com.ekta.BlogPostApp.payload.PostResponse;
import com.ekta.BlogPostApp.payload.UserSummary;

import java.time.Instant;
import java.util.Map;

public class ModelMapper {
    public static PostResponse mapPostToPostResponse(Posts post, Users creator) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getPostId());
        postResponse.setCreationDateTime(post.getCreatedAt());

        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername());
        postResponse.setCreatedBy(creatorSummary);

        return postResponse;
    }
}
