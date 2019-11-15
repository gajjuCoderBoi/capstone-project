package com.ga.postsapi.bean;

import javax.persistence.*;

@Entity
@Table(name="comments")
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

    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(name = "text")
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

}