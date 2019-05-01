package Ebamazon.model.DataAccessLayer;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.Statement;

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

    public static void main(String[] args) {
        insertNewUser("user3");
    }
}
