package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Bid;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BidDAO {

    public static ArrayList<Bid> getBidsForAuction(int auctionID){
        Connection con = DBConnection.getConnection();
        ArrayList<Bid> returnList = new ArrayList<>();
        try {
            String query = "SELECT * FROM bid WHERE auctionID=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, auctionID);
            ResultSet rs  = statement.executeQuery();

            while (rs.next()){
                Bid bid = new Bid();
                bid.setAuction(AuctionDAO.getAuctionByID(auctionID));
                bid.setAmount(rs.getBigDecimal("amount"));
                bid.setWinningBid(bitToBool(rs.getInt("winningBid")));
                bid.setOrdinaryUser(OrdinaryUserDAO.getOrdinaryUser(rs.getString("username")));
                bid.setDateTimeMade(rs.getTimestamp("dateTimeMade"));
                returnList.add(bid);
            }

        }catch (SQLException e){
            System.out.println("SQL error retrieving bids");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public static ArrayList<Bid> getWinningBids(String username){
        Connection con = DBConnection.getConnection();
        ArrayList<Bid> returnList = new ArrayList<>();
        try {
            String query = "SELECT * FROM bid WHERE username=? AND winningBid=1";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs  = statement.executeQuery();

            while (rs.next()){
                Bid bid = new Bid();
                bid.setAuction(AuctionDAO.getAuctionByID(rs.getInt("auctionID")));
                bid.setAmount(rs.getBigDecimal("amount"));
                bid.setWinningBid(true);
                bid.setOrdinaryUser(OrdinaryUserDAO.getOrdinaryUser(rs.getString("username")));
                bid.setDateTimeMade(rs.getTimestamp("dateTimeMade"));
                returnList.add(bid);
            }

        }catch (SQLException e){
            System.out.println("SQL error retrieving winning bids");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public static boolean makeBidOnAuction(Bid bid){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        try {
            String query = "INSERT INTO bid VALUES (?,?,0,?,NOW())";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, bid.getOrdinaryUser().getUsername());
            statement.setInt(2, bid.getAuction().getAuctionID());
            statement.setBigDecimal(3, bid.getAmount());
            statement.executeUpdate();
            truthFlag=true;

        }catch (SQLException e){
            System.out.println("SQL error making  bid");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return truthFlag;
    }

    public static boolean declareWinningBid(Bid bid){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        try {
            String query = "UPDATE bid SET winningBid=1 WHERE username=? AND auctionID=? AND amount=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, bid.getOrdinaryUser().getUsername());
            statement.setInt(2, bid.getAuction().getAuctionID());
            statement.setBigDecimal(3, bid.getAmount());
            statement.executeUpdate();
            truthFlag=true;

        }catch (SQLException e){
            System.out.println("SQL error declaring winning bid");
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
        ArrayList<Bid> bids = getBidsForAuction(2);
        for (Bid b : bids){
            System.out.println(b.toString());
        }
        Bid bid = bids.get(0);
        bid.setAmount(BigDecimal.valueOf(500.00));
        makeBidOnAuction(bid);
        declareWinningBid(bid);
        System.out.println("\n" + bid.toString());
        bids = getWinningBids("test username");
        for (Bid b : bids){
            System.out.println(b.toString());
        }
    }


}

