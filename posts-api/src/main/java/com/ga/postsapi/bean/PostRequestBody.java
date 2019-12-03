package com.ga.postsapi.bean;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

@ApiIgnore
public class PostRequestBody {
    private String title;
    private String text;

    /**
     * <p>getText is a getter function for field text</p>
     * @return text type String
     */
    public String getText() {
        return text;
    }

    /**
     * <p>setText(String text) is a setter function for field tet</p>
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * <p>getTitle() is a getter function for the title variable</p>
     * @return title type String
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>setTitle(String title) is the setter function for variable title</p>
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
