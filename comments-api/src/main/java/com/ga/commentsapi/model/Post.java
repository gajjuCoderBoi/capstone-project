package com.ga.commentsapi.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;



public class Post {

    /*************************************************************************
     *
     *      Post Entity which behave like a table in the database.
     *
     *      Post Entity has four properties as follows:
     *
     *      postId -> Store the id of Post (Primary Key)
     *      postTitle -> String type
     *      text -> String type
     *      userId -> Long type
     *
     *************************************************************************/


    private Long postId;



    private String title;


    private String text;


    private Long userId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }




}