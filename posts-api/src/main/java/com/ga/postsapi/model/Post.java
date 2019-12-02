package com.ga.postsapi.model;

import com.ga.postsapi.bean.Comment;
import com.ga.postsapi.bean.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *
 *      Post Entity which behave like a table in the database.
 *
 *      Post Entity has four properties as follows:
 *
 *      postId -> Store the id of Profile (Primary Key)
 *      title -> String type
 *      text -> String type
 *      userId -> Long type
 * @author Mohammad Javed and Carlos Kruger
 *************************************************************************/


@Entity
@Table(name="posts")
@ApiModel(description = "All details about the Post. ")
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database Generated Post ID")
    private Long postId;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Comment Title field can not be blank.")
    @ApiModelProperty(notes = "Title of the Post.")
    private String title;

    @Column(name = "text")
    @NotBlank(message = "Comment Body field can not be blank.")
    @ApiModelProperty(notes = "Body of the Post. ")
    private String text;

    @Column(name = "user_id", nullable = false)
    @ApiModelProperty(notes = "Id of a User who created this Post. ")
    private Long userId;

    @Transient
    @ApiModelProperty(notes = "User Object of Post.")
    private User user;

    @Transient
    @ApiModelProperty(notes = "List of Comments that belongs to this post. ")
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

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", userId=" + userId +
                '}';
    }

    public Post(Long postId,String title, String text, Long userId) {
        this.postId=postId;
        this.title = title;
        this.text = text;
        this.userId = userId;
    }


}
