package com.ga.postsapi.bean;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

@ApiIgnore
public class PostRequestBody {
    private String title;
    private String text;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
