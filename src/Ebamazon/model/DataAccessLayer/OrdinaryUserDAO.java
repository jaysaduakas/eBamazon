package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.OrdinaryUser;

import java.sql.*;

public class OrdinaryUserDAO {

    public static OrdinaryUser getOrdinaryUser(String username){
        try {
            //retrieve connection from DBConnection static method
            Connection con = DBConnection.getConnection();
            //prepare query
            String query = "SELECT * FROM OrdinaryUser WHERE username=\"" + username + "\"";

            //intialize statement and result set
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            //if the result set is not empty...
            if (rs.next()) {
                //create a new OrdinaryUser object
                OrdinaryUser ou = new OrdinaryUser();
                //set values for the new OrdinaryUser
                ou.setUsername(rs.getString("username"));
                ou.setName(rs.getString("name"));
                ou.setAddress(rs.getString("address"));
                ou.setStateID(rs.getString("stateID"));
                ou.setCc(rs.getString("creditcard"));
                ou.setPhone(rs.getString("phoneNumber"));
                ou.setApprovedStatus(bitToBool(rs.getInt("approvedStatus")));
                ou.setBannedStatus(bitToBool(rs.getInt("bannedStatus")));
                ou.setVIPStatus(bitToBool(rs.getInt("VIPStatus")));
                ou.setDateTimeRegistered(rs.getTimestamp("dateTimeRegistered"));
                ou.setSuspendedStatus(bitToBool(rs.getInt("suspendedStatus")));
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
        try {
            //retrieve connection from DBConnection static method
            Connection con = DBConnection.getConnection();
            //prepare query
            String query = "SELECT * FROM OrdinaryUser WHERE username=\"" + username + "\"";

            //intialize statement and result set
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            //if the result set is not empty...
            if (rs.next()) {
                return true;
            }
        }
        catch(SQLException e){
            System.out.println("Error retrieving ordinary user");
        }
        return false;
    }

    public static boolean setUser(OrdinaryUser ou) {
        try {
            //retrieve connection from DBConnection static method
            Connection con = DBConnection.getConnection();
            //prepare query
            String query = "UPDATE OrdinaryUser SET " +
                    "name=\"" + ou.getName() + "\", " +
                    "address=\"" + ou.getAddress() + "\", " +
                    "stateID=\"" + ou.getStateID() + "\", " +
                    "creditcard=\"" + ou.getCc() + "\", " +
                    "phoneNumber=\"" + ou.getPhone() + "\", " +
                    "approvedStatus=" + boolToBit(ou.isApprovedStatus()) + ", " +
                    "bannedStatus=" + boolToBit(ou.isBannedStatus()) + ", " +
                    "VIPStatus=" + boolToBit(ou.isVIPStatus()) + ", " +
                    "suspendedStatus=" + boolToBit(ou.isSuspendedStatus()) + " WHERE username="
                    + "\"" + ou.getUsername() + "\"";
            //intialize statement and result set
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            con.close();
            return true;
        }
        catch (Exception e){
            System.out.println("An error occurred updating the user values");
            return false;
        }
    }

    public static boolean setUserPassword(OrdinaryUser ou, String pw){
        try {
            //retrieve connection from DBConnection static method
            Connection con = DBConnection.getConnection();
            //prepare query
            String query;
            if (pw != null) {
                 query = "UPDATE OrdinaryUser SET " +
                        "password=\"" + pw + "\" WHERE username=\""
                        + ou.getUsername() + "\"";
            } else {
                 query = "UPDATE OrdinaryUser SET " +
                        "password=null WHERE username=\""
                        + ou.getUsername() + "\"";
            }
            //intialize statement and result set
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            con.close();
            return true;
        }
        catch (Exception e) {
            System.out.println("An error occurred updating the user password");
            return false;
        }
    }

    public static boolean  submitNewUser(OrdinaryUser ou){
        //retrieve connection from DBConnection static method
        Connection con = DBConnection.getConnection();
        //prepare query
        String query = "INSERT INTO OrdinaryUser VALUES (" +
                "\"" + ou.getUsername() + "\"," +
                "\"" + ou.getName() + "\", " +
                "\"" + ou.getUsername() + "\"," +
                "\"" + ou.getAddress() + "\", " +
                "\"" + ou.getStateID() + "\", " +
                "\"" + ou.getCc() + "\", " +
                "\"" + ou.getPhone() + "\", " +
                 "0, 0, 0, NOW(), 0)";
        //intialize statement and execute statement
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            con.close();
            return true;
        }
        catch (SQLException e){
            System.out.println("An error occurred inserting a new user");
            System.out.println(e.toString());
            return false;
        }
    }

    public static boolean removeUser(OrdinaryUser ou){
        boolean truthFlag = false;
        Connection con = DBConnection.getConnection();
        String query = "DELETE FROM OrdinaryUser WHERE username=?";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, ou.getUsername());
            statement.executeUpdate();
            truthFlag = true;
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

    public static boolean checkIfPasswordIsUsername(String username){
        boolean truthFlag = false;
        Connection con = DBConnection.getConnection();
        String query = "SELECT * FROM OrdinaryUser WHERE username=? AND password=?";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, username);
            ResultSet rs = statement.executeQuery();
            if  (rs.next()){
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
        return truthFlag;
    }

    public static boolean verifyOrdinaryLogin(String username, String pw){
        boolean truthFlag = false;
        try {
            String query = "SELECT * FROM OrdinaryUser WHERE username=\"" + username + "\" AND password=\"" + pw + "\" AND approvedStatus=1";
            Connection con = DBConnection.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()){
                truthFlag=true;
            }
            con.close();
        }
        catch(Exception e){
            System.out.println("Error verifying login");
        }
        return truthFlag;
    }
    private static boolean bitToBool(int i){
        return i != 0;
    }
    private static int boolToBit(boolean b) {return b ? 1 : 0; }

    public static void main(String[] args) {
        OrdinaryUser ou = OrdinaryUserDAO.getOrdinaryUser("test username");
        ou.setAddress("123 street");
        ou.setName("test name");
        ou.setUsername("NewName5");
        submitNewUser(ou);
        System.out.println(boolToBit(true));
        System.out.println(boolToBit(false));
        System.out.println(ou.toString());
    }
}
