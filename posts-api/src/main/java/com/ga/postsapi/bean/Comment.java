package com.ga.postsapi.bean;

import javax.persistence.*;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

public class Comment {

    /*************************************************************************
     *
     *      Comment Bean which behave like a table in the database.
     *
     *      Comment Bean has four properties as follows:
     *
     *      commentId -> Store the id of Profile (Primary Key)
     *      commentBody -> String type
     *      postId -> Long type
     *      userId -> Long type
     *
     *************************************************************************/

    private Long id;

    private String text;

    private Long postId;

    private Long userId;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}