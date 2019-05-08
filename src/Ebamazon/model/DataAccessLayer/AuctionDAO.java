package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Auction;
import Ebamazon.model.AuctionImage;
import Ebamazon.model.AuctionKeyword;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

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
            for (AuctionKeyword a : auction.getKeywords()){
                a.setAuction(id);
                AuctionKeywordDAO.insertAuctionKeyword(a, con);
            }
        }catch (SQLException e){
            System.out.println("SQL Exception inserting auction");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection couldnt be closed for auction insert DAO");
        }
    }

    public static ArrayList<Auction> getAuctionsByUsername(String username) throws SQLException {
        ArrayList<Auction> returnList = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try{
            String query = "SELECT * FROM auction WHERE creator=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            System.out.println("rs been returned");
            while (rs.next()){
                Auction auction = new Auction();
                auction.setAuctionID(rs.getInt("auctionID"));
                auction.setTitle(rs.getString("title"));
                auction.setOrdinaryUser(OrdinaryUserDAO.getOrdinaryUser(rs.getString("creator")));
                auction.setDateTimeCreated(rs.getTimestamp("dateTimeCreated"));
                auction.setDateTimeConfirmed(rs.getTimestamp("dateTimeConfirmed"));
                auction.setApprovalStatus(bitToBool(rs.getInt("approvalStatus")));
                auction.setLiveStatus(bitToBool(rs.getInt("liveStatus")));
                auction.setPrice(rs.getBigDecimal("price"));
                auction.setFixedOrBid(bitToBool(rs.getInt("fixedAuction")));
                auction.setDescription(rs.getString("description"));
                auction.setAuctionImages(AuctionImageDAO.getAuctionImages(auction.getAuctionID()));
                auction.setKeywords(AuctionKeywordDAO.getAuctionKeywords(auction.getAuctionID()));
                returnList.add(auction);
            }
        }catch(SQLException e){
            System.out.println("SQL Error retrieving auctions by username");
        }
        finally {
            con.close();
        }
        return returnList;
    }


    private static boolean bitToBool(int i){
        return i != 0;
    }
    private static int boolToBit(boolean b) {return b ? 1 : 0; }

}
