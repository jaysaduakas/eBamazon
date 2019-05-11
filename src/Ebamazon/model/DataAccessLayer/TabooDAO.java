package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Taboo;
import javafx.scene.control.Tab;

import java.sql.*;
import java.util.AbstractQueue;
import java.util.ArrayList;

public class TabooDAO {

    //TODO ALL INSERTED WORDS SHOULD BE SCRUBBED TO LOWER CASE

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

    public static boolean insertTaboo(Taboo taboo){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        try{
            String query = "INSERT INTO Taboo VALUES (?,?,NOW())";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, taboo.getWord().toLowerCase());
            statement.setString(2, taboo.getSuperUser().getUsername());
            statement.executeUpdate();
            truthFlag = true;
        }catch (SQLException e){
            System.out.println("SQL ERROR inserting new taboo word");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthFlag;
    }

    public static boolean deleteTaboo(String taboo){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        try{
            String query = "DELETE FROM Taboo WHERE word=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, taboo);
            statement.executeUpdate();
            truthFlag = true;
        }catch (SQLException e){
            System.out.println("SQL ERROR deleting taboo word");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return truthFlag;
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
