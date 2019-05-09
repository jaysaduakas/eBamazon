package Ebamazon.model;

import java.sql.Timestamp;

public class Ratings {
    private String Rater;
    private String Ratee;
    private double Rating;
    private Timestamp DateTimeRated;

    public Ratings(){

    }

    public Ratings(String rater, String ratee, double rating, Timestamp dateTimeRated) {
        Rater = rater;
        Ratee = ratee;
        Rating = rating;
        DateTimeRated = dateTimeRated;
    }
    public String getRater() {
        return Rater;
    }
    public void setRater(String rater) {
        Rater = rater;
    }
    public String getRatee() {
        return Ratee;
    }
    public void setRatee(String ratee) {
        Ratee = ratee;
    }
    public double getRating() {
        return Rating;
    }
    public void setRating(double rating) {
        Rating = rating;
    }
    public Timestamp getDateTimeRated() {
        return DateTimeRated;
    }
    public void setDateTimeRated(Timestamp dateTimeRated) {
        DateTimeRated = dateTimeRated;
    }
}
