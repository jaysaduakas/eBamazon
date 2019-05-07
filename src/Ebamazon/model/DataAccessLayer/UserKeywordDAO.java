package Ebamazon.model.DataAccessLayer;


import Ebamazon.model.UserKeyword;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserKeywordDAO {

    public static boolean submitKeyword(UserKeyword uk) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO UserKeyword VALUES (\"" +
                    uk.getUsername() + "\", \"" + uk.getKeyword() + "\")";
            if (!keywordExists(uk.getUsername(),uk.getKeyword(), con)) {
                Statement statement = con.createStatement();
                statement.executeUpdate(query);
            }
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("An error occurred submitting keyword.");
        }
        return false;
    }

    public static ArrayList<UserKeyword> getKeywords(String username) {
        ArrayList<UserKeyword> userKeywords = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM UserKeyword WHERE username=\"" +
                    username + "\"";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                UserKeyword u = new UserKeyword();
                u.setUsername(username);
                u.setKeyword(rs.getString("keyword"));
                userKeywords.add(u);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("An error occurred retrieving keywords.");
        }
        return userKeywords;
    }

    public static boolean deleteKeyword(UserKeyword uk){
        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM UserKeyword WHERE username=\"" +
                    uk.getUsername() + "\" AND keyword=\"" + uk.getKeyword() + "\"";
            if (keywordExists(uk.getUsername(),uk.getKeyword(), con)) {
                Statement statement = con.createStatement();
                statement.executeUpdate(query);
            }
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("An error occurred deleting keyword.");
        }
        return false;
    }

    private static boolean keywordExists(String username, String keyword, Connection con){
        try {
            String query = "SELECT * FROM UserKeyword WHERE username=\"" +
                    username + "\" AND keyword=\"" + keyword + "\"";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("An error occurred submitting keyword.");
        }
        return false;
    }


    public static void main(String[] args) {
        UserKeyword kw = new UserKeyword();
        kw.setUsername("test username");
        kw.setKeyword("testTerm");
        submitKeyword(kw);
        submitKeyword(kw);
        submitKeyword(kw);
        ArrayList<UserKeyword> list = getKeywords("test username");
        for (UserKeyword k : list){
            System.out.println(k.toString());
        }
    }
}
