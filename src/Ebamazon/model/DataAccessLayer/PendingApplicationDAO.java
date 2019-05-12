package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.PendingApplications;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class PendingApplicationDAO {

    public static boolean insertNewUser(String username){
        Connection con = DBConnection.getConnection();
        String query = "INSERT INTO PendingApplication VALUES (\"" + username + "\", NOW())";
        try{
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            return true;
        }
        catch (Exception e){
            System.out.println("Error inserting pending application");
            return false;
        }
    }

    public static ArrayList<PendingApplications> getAllPending() throws SQLException {
        ArrayList<PendingApplications> returnList = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        String query = "SELECT * FROM pendingApplication ORDER BY dateTimeSubmitted";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                PendingApplications p = new PendingApplications();
                p.setDateTimeSubmitted(rs.getTimestamp("dateTimeSubmitted"));
                p.setUserID(rs.getString("username"));
                returnList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
        return returnList;
    }

    public static boolean removeApplication(PendingApplications pendingApplication){

        boolean truthFlag = false;
        Connection con = DBConnection.getConnection();
        String query = "DELETE FROM pendingApplication WHERE username=?";

        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, pendingApplication.getUserID());
            statement.executeUpdate();
            con.close();
            truthFlag=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthFlag;
    }

    public static void main(String[] args) {
        insertNewUser("user3");
    }
}
