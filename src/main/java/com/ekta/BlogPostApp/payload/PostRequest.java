package com.ekta.BlogPostApp.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostRequest {

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 250)
    private String image;

    @NotNull
    @Size(min = 2, max = 6)
    @Valid
    private String tag;

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getTag() {
        return tag;
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
}
