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
        ArrayList<Ratings> ratings = RatingsDAO.getAllRatingsForUser(username);
        if(ratings.size() == 0){
            return -1;
        }
        double sum = 0;
        int divisor = 0;
        for (Ratings r : ratings){
            sum+= r.getRating();
            divisor++;
        }
        System.out.println(username +  "'s rating is " + sum/divisor);
        return sum/divisor;
    }
    public static boolean isRatedByAuctionID(int auctionID){

        return RatingsDAO.alreadyRated(auctionID);
    }

    public static boolean isReckless(String username){
        ArrayList<Ratings> ratings = getLastThreeByUser(username);
        System.out.println("Ratings Size returned by last 3: " + ratings.size());  //should be 1
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
        System.out.println("Sum: " + sum + "\nNumberLow: " + numberLow);
        System.out.println("Is Reckless Returns: " + (sum >= 15 || numberLow >= 3));
        return (sum >= 15 || numberLow >= 3);
    }

    private static ArrayList<Ratings> getLastThreeByUser(String username) {return RatingsDAO.getLastThreeRatingsByRater(username); }


    public static boolean userHasThreeDifferentRaters(String username){
        HashSet<String> noDupSet = new HashSet<>();
        for (Ratings r : RatingsDAO.getAllRatingsForUser(username)){
            noDupSet.add(r.getRater());
        }
        System.out.println("HashSet Size is: " + noDupSet.size()); //should be 1
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
