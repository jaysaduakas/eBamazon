package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Complaint;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ComplaintDAO {

    public static void submitComplaint(Complaint c){
        try{
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO Complaint VALUES (\"" +
                    c.getSender() + "\", \"" +
                    c.getComplainee() + "\", \"" +
                    c.getComplaint() + "\", NOW(), 0, NULL)";
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException e){
            System.out.println("Error submitting complaint");
        }
    }

    public static void main(String[] args) {
        Complaint c = new Complaint();
        c.setSender("test username");
        c.setComplainee("test username");
        c.setComplaint("This dude is the worst.  Like, seriously the worst.");
        submitComplaint(c);
    }
}
