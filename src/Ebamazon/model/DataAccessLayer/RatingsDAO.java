package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Ratings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RatingsDAO {

    public static ArrayList<Ratings> getLastThreeRatingsByRater(String username){
        Connection con = DBConnection.getConnection();
        ArrayList<Ratings> lastThree = new ArrayList<>();
        String query = "SELECT * FROM rating WHERE rater=? ORDER BY dateTimeRated DESC";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            for (int i = 0; i < 3; i ++){
                if (rs.next()){
                    Ratings ratings = new Ratings();
                    ratings.setRating((double)rs.getInt("rating"));
                    ratings.setRatee(rs.getString("ratee"));
                    ratings.setRater(rs.getString("rater"));
                    ratings.setAuctionID(rs.getInt("auctionID"));
                    ratings.setDateTimeRated(rs.getTimestamp("dateTimeRated"));
                    lastThree.add(ratings);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastThree;
    }


    public static ArrayList<Ratings> getLastThreeRatingsByRatee(String username){
        Connection con = DBConnection.getConnection();
        ArrayList<Ratings> lastThree = new ArrayList<>();
        String query = "SELECT * FROM rating WHERE ratee=? ORDER BY dateTimeRated DESC";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            for (int i = 0; i < 3; i ++){
                if (rs.next()){
                    Ratings ratings = new Ratings();
                    ratings.setRating((double)rs.getInt("rating"));
                    ratings.setRatee(rs.getString("ratee"));
                    ratings.setRater(rs.getString("rater"));
                    ratings.setAuctionID(rs.getInt("auctionID"));
                    ratings.setDateTimeRated(rs.getTimestamp("dateTimeRated"));
                    lastThree.add(ratings);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastThree;
    }

    public static boolean alreadyRated(int auctionID){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        String query = "SELECT * FROM rating WHERE auctionID=?";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, auctionID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                truthFlag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  truthFlag;
    }

    public static boolean submitRating(Ratings ratings){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        String query = "INSERT INTO rating VALUES (?,?,?,?,NOW())";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, ratings.getAuctionID());
            statement.setString(2, ratings.getRater());
            statement.setString(3, ratings.getRatee());
            statement.setInt(4, (int)ratings.getRating());
            statement.executeUpdate();
            truthFlag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthFlag;
    }

    public static ArrayList<Ratings> getAllRatingsForUser(String username){
        Connection con = DBConnection.getConnection();
        ArrayList<Ratings> lastThree = new ArrayList<>();
        String query = "SELECT * FROM rating WHERE ratee=? ORDER BY dateTimeRated DESC";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Ratings ratings = new Ratings();
                ratings.setRating((double)rs.getInt("rating"));
                ratings.setRatee(rs.getString("ratee"));
                ratings.setRater(rs.getString("rater"));
                ratings.setAuctionID(rs.getInt("auctionID"));
                ratings.setDateTimeRated(rs.getTimestamp("dateTimeRated"));
                lastThree.add(ratings);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastThree;
    }
}
