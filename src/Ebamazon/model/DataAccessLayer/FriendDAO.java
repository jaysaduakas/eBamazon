package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Friends;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendDAO {

    public static ArrayList<Friends> getConfirmedFriends(String username){
        Connection con = DBConnection.getConnection();
        ArrayList<Friends> returnList = new ArrayList<>();
        try {
            String query = "SELECT * FROM friend WHERE (usernameSuggesting=? OR usernameConfirming=?) AND friendshipConfirmed=1";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Friends friend = new Friends();
                friend.setConfirmingFriendID(rs.getString("usernameConfirming"));
                friend.setSuggestingFriendlD(rs.getString("usernameSuggesting"));
                friend.setFriendshipConfirmed(true);
                friend.setDateTimeConfirmed(rs.getTimestamp("dateTimeConfirmed"));
                returnList.add(friend);
            }

        }catch(SQLException e){
            System.out.println("SQL error retrieving confirmed friends");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public static ArrayList<Friends> getFriendRequests(String username){
        Connection con = DBConnection.getConnection();
        ArrayList<Friends> returnList = new ArrayList<>();
        try {
            String query = "SELECT * FROM friend WHERE usernameConfirming=? AND friendshipConfirmed=0";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Friends friend = new Friends();
                friend.setConfirmingFriendID(rs.getString("usernameConfirming"));
                friend.setSuggestingFriendlD(rs.getString("usernameSuggesting"));
                friend.setFriendshipConfirmed(false);
                friend.setDateTimeConfirmed(rs.getTimestamp("dateTimeConfirmed"));
                returnList.add(friend);
            }

        }catch(SQLException e){
            System.out.println("SQL error retrieving unconfirmed friends");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnList;
    }


    public static boolean insertFriendRequest(Friends friend){
        Connection con = DBConnection.getConnection();
        boolean truthflag = false;

        if (!checkConfirmedFriends(friend) && !checkUnconfirmedFriends(friend)) {
            try {
                String query = "INSERT INTO friend VALUES (?,?,?,NOW())";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, friend.getSuggestingFriendlD());
                statement.setString(2, friend.getConfirmingFriendID());
                statement.setInt(3, 0);

                statement.executeUpdate();
                truthflag = true;
            } catch (SQLException e) {
                System.out.println("SQL error inserting friend request");
            }
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthflag;
    }

    public static boolean checkConfirmedFriends(Friends friend){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        try {
            String query = "SELECT * FROM friend WHERE ((usernameSuggesting=? AND usernameConfirming=?) OR (usernameSuggesting=? AND usernameConfirming=?)) AND friendshipConfirmed=1";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,friend.getConfirmingFriendID());
            statement.setString(2,friend.getSuggestingFriendlD());
            statement.setString(3,friend.getSuggestingFriendlD());
            statement.setString(4,friend.getConfirmingFriendID());

            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                truthFlag = true;
            }

        }catch(SQLException e){
            System.out.println("SQL error retrieving confirmed friends");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return truthFlag;
    }

    public static boolean checkUnconfirmedFriends(Friends friend){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        try {
            String query = "SELECT * FROM friend WHERE ((usernameSuggesting=? AND usernameConfirming=?) OR (usernameSuggesting=? AND usernameConfirming=?)) AND friendshipConfirmed=0";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,friend.getConfirmingFriendID());
            statement.setString(2,friend.getSuggestingFriendlD());
            statement.setString(3,friend.getSuggestingFriendlD());
            statement.setString(4,friend.getConfirmingFriendID());

            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                truthFlag = true;
            }

        }catch(SQLException e){
            System.out.println("SQL error retrieving confirmed friends");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return truthFlag;
    }



    public static boolean confirmFriendRequest(Friends friend){
        Connection con = DBConnection.getConnection();
        boolean truthflag = false;

        try {
                String query = "UPDATE friend SET friendshipConfirmed=1 WHERE usernameSuggesting=? AND usernameConfirming=?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, friend.getSuggestingFriendlD());
                statement.setString(2, friend.getConfirmingFriendID());

                statement.executeUpdate();
                truthflag = true;
            } catch (SQLException e) {
                System.out.println("SQL error accepting friend request");
        }


        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthflag;
    }

    public static boolean deleteFriend(Friends friend){
        Connection con = DBConnection.getConnection();
        boolean truthflag = false;

        if (checkConfirmedFriends(friend)) {
            try {
                String query = "DELETE FROM friend WHERE usernameSuggesting=? AND usernameConfirming=?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, friend.getSuggestingFriendlD());
                statement.setString(2, friend.getConfirmingFriendID());

                statement.executeUpdate();
                truthflag = true;
            } catch (SQLException e) {
                System.out.println("SQL error deleting friend");
            }
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthflag;
    }
}
