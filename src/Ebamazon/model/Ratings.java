package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.RatingsDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

public class Ratings {
    private String Rater;
    private String Ratee;
    private double Rating;
    private Timestamp DateTimeRated;
    private int auctionID;

    public Ratings(){

    }

    public Ratings(String rater, String ratee, double rating, Timestamp dateTimeRated) {
        Rater = rater;
        Ratee = ratee;
        Rating = rating;
        DateTimeRated = dateTimeRated;
    }


    //ratings functions

    public boolean insertRating(){ return RatingsDAO.submitRating(this);}

    public static double getAverageRating(String username){
        double sum = 0;
        int divisor = 0;
        for (Ratings r : RatingsDAO.getAllRatingsForUser(username)){
            sum+= r.getRating();
            divisor++;
        }
        return sum/divisor;
    }

    public static boolean isReckless(String username){
        ArrayList<Ratings> ratings = getLastThreeByUser(username);

        double sum = 0;
        int numberLow = 0;
        if (ratings.size() >= 3) {
            for (Ratings r : ratings) {
                if (r.getRating()<=1) numberLow++;
                sum += r.getRating();
            }
        }
        else {
            return false;
        }

        return (sum >= 15 || numberLow >= 3);
    }

    private static ArrayList<Ratings> getLastThreeByUser(String username) {return RatingsDAO.getLastThreeRatings(username); }


    public static boolean userHasThreeDifferentRaters(String username){
        HashSet noDupSet = new HashSet();
        for (Ratings r : RatingsDAO.getAllRatingsForUser(username)){
            noDupSet.add(r.getRater());
        }
        return (noDupSet.size() >= 3);
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
    public int getAuctionID() {
        return auctionID;
    }
    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }
}
