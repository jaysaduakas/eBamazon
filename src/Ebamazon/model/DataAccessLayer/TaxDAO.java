package Ebamazon.model.DataAccessLayer;

import com.mysql.cj.xdevapi.DbDoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxDAO {

    public static double getTaxRate(String abbr){
        double rate = 0;
        Connection con = DBConnection.getConnection();
        try{
            String query = "SELECT rate FROM TaxRate Where stateID=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,abbr);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                rate = rs.getDouble("rate");
            }

        }catch(SQLException e){
            System.out.println("SQL Error retrieving tax rate");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rate;
    }
}
