package com.ga.commentsapi.model;

import com.ga.commentsapi.bean.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "comments")
public class Comment {

    /*************************************************************************
     *
     *      Comment Entity which behave like a table in the database.
     *
     *      Comment Entity has four properties as follows:
     *
     *      commentId -> Store the id of Profile (Primary Key)
     *      commentBody -> String type
     *      postId -> Long type
     *      userId -> Long type
     *
     *************************************************************************/

    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(name = "text")
    @NotBlank(message = "Comment Body field cannot be blanked. ")
    private String text;

    @Column(name = "postId")
    private Long postId;

    @Column(name = "userId")
    private Long userId;

    @Transient
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

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", postId=" + postId +
                ", userId=" + userId +
                '}';
    }

    public Comment(String text, Long postId, Long userId) {
        this.text = text;
        this.postId = postId;
        this.userId = userId;
    }
}