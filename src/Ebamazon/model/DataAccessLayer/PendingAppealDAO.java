package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.PendingAppeal;
import Ebamazon.model.PendingApplications;

import java.sql.*;
import java.util.ArrayList;

public class PendingAppealDAO {


    public static boolean insertNewAppeal(PendingAppeal pa){
        Connection con = DBConnection.getConnection();
        String query = "INSERT INTO PendingAppeals VALUES (?,?, NOW())";
        try{
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, pa.getUsername());
            statement.setString(2, pa.getText());
            statement.executeUpdate();
            return true;
        }
        catch (Exception e){
            System.out.println("Error inserting pending appeal");
            return false;
        }
    }

    public static ArrayList<PendingAppeal> getAllPending() throws SQLException {
        ArrayList<PendingAppeal> returnList = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        String query = "SELECT * FROM pendingAppeals ORDER BY dateTimeAppealed";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                PendingAppeal p = new PendingAppeal();
                p.setDateTimeAppealed(rs.getTimestamp("dateTimeAppealed"));
                p.setUsername(rs.getString("username"));
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
}
