package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Complaint;

import java.sql.*;
import java.util.ArrayList;

public class ComplaintDAO {

    public static void submitComplaint(Complaint c){
        try{
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO Complaint VALUES (\"" +
                    c.getSender() + "\", \"" +
                    c.getComplainee() + "\", \"" +
                    c.getComplaint() + "\", NOW(), 0, NULL, 0, NULL)";
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException e){
            System.out.println("Error submitting complaint");
        }
    }

    public static ArrayList<Complaint> getComplaineeComplaints(String username){
        Connection con = DBConnection.getConnection();
        ArrayList<Complaint> returnList = new ArrayList<>();
        String query = "SELECT * FROM Complaint WHERE receiver=? AND complaineeResponded=0";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Complaint complaint = new Complaint();
                complaint.setSender(rs.getString("sender"));
                complaint.setComplainee(rs.getString("receiver"));
                complaint.setComplaint(rs.getString("complaint"));
                complaint.setAlreadyJustified(bitToBool(rs.getInt("alreadyJustified")));
                complaint.setDateTimeSent(rs.getTimestamp("dateTimeMade"));
                complaint.setSuperUser(rs.getString("usernameSuper"));
                complaint.setComplaineeResponded(false);
                complaint.setComplaineeResponse("");
                returnList.add(complaint);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public static boolean updateComplaint(Complaint c){
        boolean truthFlag = false;
        Connection con = DBConnection.getConnection();
        try{
            String query = "UPDATE complaint SET complaineeResponded=1, complaineeResponse=? WHERE sender=? AND receiver=? AND dateTimeMade=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, c.getComplaineeResponse());
            statement.setString(2, c.getSender());
            statement.setString(3, c.getComplainee());
            statement.setTimestamp(4, c.getDateTimeSent());
            statement.executeUpdate();
            truthFlag = true;
        }
        catch (SQLException e){
            System.out.println("Error submitting complaint");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthFlag;
    }

    private static boolean bitToBool(int i){
        return i != 0;
    }
    private static int boolToBit(boolean b) {return b ? 1 : 0; }

    public static void main(String[] args) {
        Complaint c = new Complaint();
        c.setSender("test username");
        c.setComplainee("test username");
        c.setComplaint("This dude is the worst.  Like, seriously the worst.");
        submitComplaint(c);
    }
}
