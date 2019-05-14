package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Warning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WarningDAO {

    public static boolean insertWarning(Warning warning){
        boolean truthFlag = false;
        Connection con = DBConnection.getConnection();
        String query = "INSERT INTO warning VALUES (?,?,?,NOW())";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, warning.getOrdinaryUser().getUsername());
            if (warning.getSuperUser()!=null) statement.setString(2, warning.getSuperUser().getUsername());
            else statement.setString(2,"AutoMod");
            statement.setString(3, warning.getReason());
            statement.executeUpdate();
            truthFlag = true;
        } catch (SQLException e){
            System.out.println("SQL Error inserting warning");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthFlag;
    }


    public static ArrayList<Warning> getWarningsByUser(String username){
        ArrayList<Warning> warnings = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        String query = "SELECT * FROM Warning WHERE usernameOrdinary=?";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Warning warning = new Warning();
                warning.setOrdinaryUser(OrdinaryUserDAO.getOrdinaryUser(rs.getString("usernameOrdinary")));
                warning.setSuperUser(SuperUserDAO.getSuperUser(rs.getString("usernameSuper")));
                warning.setReason(rs.getString("reason"));
                warning.setDateTimeIssued(rs.getTimestamp("dateTimeIssued"));
                warnings.add(warning);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warnings;

    }


    public static void main(String[] args) {
        Warning warning= new Warning();
        warning.setOrdinaryUser(OrdinaryUserDAO.getOrdinaryUser("test username"));
        warning.setSuperUser(SuperUserDAO.getSuperUser("super"));
        warning.setReason("You a lil bitch");

        insertWarning(warning);

        for (Warning w : getWarningsByUser("test username")){
            System.out.println();
        }
    }

}
