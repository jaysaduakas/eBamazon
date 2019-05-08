package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Auction;
import Ebamazon.model.AuctionImage;
import Ebamazon.model.AuctionKeyword;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuctionKeywordDAO {

    public static boolean insertAuctionKeyword(AuctionKeyword ak, Connection con){
        try{
            String query = "INSERT INTO auctionkeyword VALUES (?,?)";

            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, ak.getAuction());
            statement.setString(2, ak.getKeyword());
            statement.executeUpdate();
            return true;
        }
        catch(SQLException e){
            System.out.println("SQL error inserting auction keyword");
        }
        return false;
    }

    public static ArrayList<AuctionKeyword> getAuctionKeywords(int auctionID) throws SQLException {
        ArrayList<AuctionKeyword> returnList = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try{
            String query = "SELECT * FROM AuctionKeyword WHERE auctionID=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,auctionID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                AuctionKeyword ak = new AuctionKeyword();
                ak.setAuction(auctionID);
                ak.setKeyword(rs.getString("keyword"));
                returnList.add(ak);
            }
        }catch(SQLException e){
            System.out.println("SQL error retrieving keywords");
        }
        con.close();
        return returnList;
    }
}
