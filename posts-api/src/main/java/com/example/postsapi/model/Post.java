package com.example.postsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/*************************************************************************
 *
 *      Post Entity which behave like a table in the database.
 *
 *      Post Entity has four properties as follows:
 *
 *      postId -> Store the id of Profile (Primary Key)
 *      title -> String type
 *      text -> String type
 *      userId -> Long type
 *
 *************************************************************************/


@Entity
@Table(name="posts")
public class Post {

    @JsonIgnore
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;


    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "user_id", nullable = false)
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
