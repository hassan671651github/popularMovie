package com.example.ahmed.finalprojectmal;

import java.util.ArrayList;

/**
 * Created by Ahmed on 21/09/2016.
 */
public class MovieDB {
    private String Title;
    private String Poster;
    private String Date;
    private String Description;
    private String Rating;
    private String Time;
    private int id;
    private ArrayList<Trailer> trailerses;
    private ArrayList<reviews>reviewses;

    public ArrayList<Trailer> getTrailerses() {
        return trailerses;
    }

    public void setTrailerses(ArrayList<Trailer> trailerses) {
        this.trailerses = trailerses;
    }

    public ArrayList<reviews> getReviewses() {
        return reviewses;
    }

    public void setReviewses(ArrayList<reviews> reviewses) {
        this.reviewses = reviewses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public MovieDB(){

    }
    public MovieDB(String poster) {
        Poster = poster;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}
