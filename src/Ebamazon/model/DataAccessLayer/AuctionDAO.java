package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Auction;
import Ebamazon.model.AuctionImage;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuctionDAO {

    public static void insertAuction(Auction auction){
        Connection con = DBConnection.getConnection();
        try{
            String query = "INSERT INTO Auction (title, creator, dateTimeCreated,approvalStatus,liveStatus,price,fixedAuction,description) VALUES (\"" +
                    auction.getTitle() + "\", \"" +
                    auction.getOrdinaryUser().getUsername() + "\", NOW(), 0, 0, " +
                    auction.getPrice() + ", " +
                    boolToBit(auction.isFixedOrBid()) + ", \"" +
                    auction.getDescription() + "\")";
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            String idstring = "SELECT LAST_INSERT_ID();";
            Statement idStatement = con.createStatement();
            ResultSet rs = idStatement.executeQuery(idstring);
            int id = 0;

            if (rs.next()){
                id = rs.getInt("LAST_INSERT_ID()");
            }
            for (AuctionImage a : auction.getAuctionImages()){
                a.setAuction(id);
                AuctionImageDAO.insertAuctionImage(a,con);
            }
        }catch (SQLException e){
            System.out.println("SQL Exception inserting auction");
        }
    }


    private static boolean bitToBool(int i){
        return i != 0;
    }
    private static int boolToBit(boolean b) {return b ? 1 : 0; }

}
