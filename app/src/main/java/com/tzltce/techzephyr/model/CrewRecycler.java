package com.tzltce.techzephyr.model;

/**
 * Created by Chetan on 25-12-2015.
 */
public class CrewRecycler {
    protected String name;
    protected String post;
    protected String number;

    public void setName(String name) {
        this.name = name;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getName() {
        return this.name;
    }
    public String getPost() {
        return this.post;
    }
    public String getNumber() {
        return this.number;
    }
}
