package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.OrdinaryUser;
import Ebamazon.model.SuperUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SuperUserDAO {

    public static SuperUser getSuperUser(String username){
        try {
            //retrieve connection from DBConnection static method
            Connection con = DBConnection.getConnection();
            //prepare query
            String query = "SELECT * FROM SuperUser WHERE username=\"" + username + "\"";

            //intialize statement and result set
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            //if the result set is not empty...
            if (rs.next()) {
                //create a new OrdinaryUser object
                SuperUser ou = new SuperUser();
                //set values for the new OrdinaryUser
                ou.setUsername(rs.getString("username"));
                ou.setName(rs.getString("name"));
                //close db connection
                con.close();
                //return new user object
                return ou;
            }
        }
        catch(SQLException e){
            System.out.println("Error retrieving ordinary user");
        }
        return null;
    }

    public static boolean checkExists(String username){

        //retrieve connection from DBConnection static method
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        try {

            //prepare query
            String query = "SELECT * FROM SuperUser WHERE username=\"" + username + "\"";

            //intialize statement and result set
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            //if the result set is not empty...
            if (rs.next()) {
                truthFlag = true;
            }
        }
        catch(SQLException e){
            System.out.println("Error retrieving ordinary user");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthFlag;
    }


public static boolean checkPassword(String username, String password){
        Connection con = DBConnection.getConnection();
        boolean returnFlag = false;
        try{
            String query = "SELECT * FROM superuser WHERE username=\"" + username + "\" AND password=\"" + password + "\"";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()){
                returnFlag = true;
            }

        }catch (SQLException e){
            System.out.println("SQL Error validating superuser password");
        }

    try {
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return returnFlag;
}

    public static void main(String[] args) {

    }

}
