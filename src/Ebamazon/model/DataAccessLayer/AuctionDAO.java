package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.*;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

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

    public static ArrayList<AuctionResult> getAuctionsByParameter(SearchParameters sp) throws SQLException {
        ArrayList<AuctionResult> returnList = new ArrayList<>();
        ArrayList<Integer> auctionIDList = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try{
            for (String keyword : sp.getSearchQuery()) {
                String query;
                query = "SELECT * FROM auctionKeyword NATURAL JOIN auction WHERE (keyword LIKE ? OR title LIKE ?) AND (price BETWEEN ? AND ?) ";
                if (sp.isShowAuction() && !sp.isShowFixed()){
                    query += " AND fixedAuction=0";
                }
                else if (!sp.isShowAuction() && sp.isShowFixed()){
                    query += " AND fixedAuction=1";
                }
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, "%" + keyword + "%");
                statement.setString(2, "%" + keyword + "%");
                statement.setBigDecimal(3, sp.getMinPrice());
                statement.setBigDecimal(4, sp.getMaxPrice());
                ResultSet rs = statement.executeQuery();

                while (rs.next()){
                    auctionIDList.add(rs.getInt("auctionID"));
                }
            }
            //Have a list with relevant AuctionIDs, with a single entry per 'Point'
            //set each occurring auctionID, with score == # of times it occurs in list
            returnList = generateReturnList(auctionIDList);

        }catch (SQLException e){
            System.out.println("sql error searching FAM");
        }
        finally {
            con.close();
        }
        return returnList;
    }

    private static ArrayList<AuctionResult> generateReturnList(ArrayList<Integer> auctionIDList) throws SQLException {
        ArrayList<AuctionResult> returnList = new ArrayList<>();
        //if list not empty...
        if (auctionIDList.size() > 0) {
            //sort it
            Collections.sort(auctionIDList);

            //intitlize count and index
            int count = 1;
            int curIndex = 0;
            //look up first auctionID in DB, and add result to return list
            returnList.add(getAuctionByID(auctionIDList.get(0)));
            int curAuctionID = auctionIDList.get(0);
            //for the rest of them...
            for (int i=1; i < auctionIDList.size();i++){
                if (curAuctionID!=auctionIDList.get(i)){
                    returnList.get(curIndex).setScore(count);
                    curIndex++;
                    count = 0;
                    curAuctionID = auctionIDList.get(i);
                    returnList.add(getAuctionByID(auctionIDList.get(i)));
                }
                count++;
            }
            returnList.get(curIndex).setScore(count);
        }
        return returnList;
    }

    public static AuctionResult getAuctionByID(int id) throws SQLException {
        Connection con = DBConnection.getConnection();
        AuctionResult auction = new AuctionResult();
        try {
            String query = "SELECT * FROM auction WHERE auctionID=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
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
            }
        }catch (SQLException e){
            System.out.println("SQL error retrieving auction by ID");
        }
        finally {
            con.close();
        }
        return auction;
    }


    private static boolean bitToBool(int i){
        return i != 0;
    }
    private static int boolToBit(boolean b) {return b ? 1 : 0; }


    public static void main(String[] args) throws SQLException {
        SearchParameters sp = new SearchParameters("glass water bottle");
        SearchParameters sp2 = new SearchParameters("big table");
        SearchParameters sp3 = new SearchParameters("big glass table");
        ArrayList<AuctionResult> results = getAuctionsByParameter(sp);
        System.out.println("Search 1 :");
        for (AuctionResult r : results){
            System.out.println((Integer.toString(r.getAuctionID()) + ":" + Integer.toString(r.getScore())));
        }
        results = getAuctionsByParameter(sp2);
        System.out.println("Search 2 :");
        for (AuctionResult r : results){
            System.out.println((Integer.toString(r.getAuctionID()) + ":" + Integer.toString(r.getScore())));
        }
        results = getAuctionsByParameter(sp3);
        System.out.println("Search 3 :");
        for (AuctionResult r : results){
            System.out.println((Integer.toString(r.getAuctionID()) + ":" + Integer.toString(r.getScore())));
        }
    }
}
