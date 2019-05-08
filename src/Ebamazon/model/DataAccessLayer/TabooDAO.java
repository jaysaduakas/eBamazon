package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Taboo;
import javafx.scene.control.Tab;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractQueue;
import java.util.ArrayList;

public class TabooDAO {

    public static ArrayList<String> getTabooWords() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try{
            String query = "SELECT word FROM Taboo";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                list.add(rs.getString("word"));
            }
        }catch(SQLException e){
            System.out.println("Error retrieving taboo word list");
        }
        finally {
            con.close();
        }
        return  list;
    }

    public static void main(String[] args) {
        try {
            for (String s : getTabooWords()){
                System.out.println(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
