package com.ga.postsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

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

    @Transient
    private User user;

    @Transient
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
