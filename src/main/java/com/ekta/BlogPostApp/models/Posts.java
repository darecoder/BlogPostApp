package com.ekta.BlogPostApp.models;

import javax.persistence.*;
import java.util.Set;

@Table(name = "Posts")
@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PostID")
    private int postId;

    @Column(name = "ImageUrl")
    private String image;

    @Column(name = "Tag")
    private String tag;

//    @Column(name = "UserID")
//    private int userId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Users_Posts",
            joinColumns = @JoinColumn(name = "PostId"),
            inverseJoinColumns = @JoinColumn(name = "UserID"))
    private Set<Posts> posts;

    public Set<Posts> getPosts() {
        return posts;
    }

    public void setPosts(Set<Posts> posts) {
        this.posts = posts;
    }

    public Posts() {
    }

    public int getPostId() {
        return postId;
    }

    public String getImage() {
        return image;
    }

    public String getTag() {
        return tag;
    }

//    public int getUserId() {
//        return userId;
//    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
}
