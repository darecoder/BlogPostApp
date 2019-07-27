package com.ekta.BlogPostApp.models;

import com.ekta.BlogPostApp.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Table(name = "Posts")
@Entity
public class Posts extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostID")
    private long postId;

    @Column(name = "Title")
    private String title;

    @Column(name = "ImageUrl")
    private String image;

    @Column(name = "Tag")
    private String tag;

    @NotNull
    private Instant expirationDateTime;

    public Posts() {
    }

    public long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getTag() {
        return tag;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }
}
