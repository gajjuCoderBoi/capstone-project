package com.ga.postsapi.bean;

import javax.persistence.*;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

public class Comment {

    /**
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

    /**
     * <p>getUser() is a getter function. It will return the user. It does not take a parameter
     * @return user User type</p>
     */

    public User getUser() {
        return user;
    }


    /**
     * <p>setUser(User user) is a setter function.</p>
     * @param user
     */

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * <p>getId() is a getter function that will return the Id</p>
     * @return id Long type
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>setId(Long id) is a setter function</p>
     * @param id Type Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>getText() is a getter function.</p>
     * @return text Type  String
     */

    public String getText() {
        return text;
    }

    /**
     * <p>setText(String text) is a setter type for the variable text</p>
     * @param text Type String
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * <p>getPostId() is a getter function for PostId</p>
     * @return posId type Long
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * <p>setPostId(Long postId) is a setter function for the postId variable</p>
     * @param postId type Long
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * <p>getUserId() is a getter function for userId</p>
     * @return userId type Long
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * <p>setUserId(Long userId) is a setter function for userId</p>
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}