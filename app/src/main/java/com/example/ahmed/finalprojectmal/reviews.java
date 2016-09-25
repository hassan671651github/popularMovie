package com.example.ahmed.finalprojectmal;

/**
 * Created by Ahmed on 23/09/2016.
 */
public class reviews {
    private String Auther;
    private String Content;

    public reviews() {
    }

    public reviews(String auther, String content) {
        Auther = auther;
        Content = content;
    }

    public String getAuther() {
        return Auther;
    }

    public void setAuther(String auther) {
        Auther = auther;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
