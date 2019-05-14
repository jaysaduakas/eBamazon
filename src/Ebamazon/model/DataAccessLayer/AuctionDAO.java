package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class AuctionDAO {

    public static void insertAuction(Auction auction){
        Connection con = DBConnection.getConnection();
        try{
            String query = "INSERT INTO Auction (title, creator, dateTimeCreated,approvalStatus,liveStatus,price,fixedAuction,description,kickedBack, denied) VALUES (\"" +
                    auction.getTitle() + "\", \"" +
                    auction.getOrdinaryUser().getUsername() + "\", NOW(), 0, 0, " +
                    auction.getPrice() + ", " +
                    boolToBit(auction.isFixed()) + ", \"" +
                    auction.getDescription() + "\", " +
                    boolToBit(auction.isKickback()) + ", 0)";
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
            System.out.println(e.toString());
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
                auction.setFixed(bitToBool(rs.getInt("fixedAuction")));
                auction.setDescription(rs.getString("description"));
                auction.setAuctionImages(AuctionImageDAO.getAuctionImages(auction.getAuctionID()));
                auction.setKeywords(AuctionKeywordDAO.getAuctionKeywords(auction.getAuctionID()));
                auction.setKickback(bitToBool(rs.getInt("kickedBack")));
                auction.setDenied(bitToBool(rs.getInt("denied")));
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

    public static AuctionResult getAuctionByID(int id) {
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
                auction.setFixed(bitToBool(rs.getInt("fixedAuction")));
                auction.setDescription(rs.getString("description"));
                auction.setAuctionImages(AuctionImageDAO.getAuctionImages(auction.getAuctionID()));
                auction.setKeywords(AuctionKeywordDAO.getAuctionKeywords(auction.getAuctionID()));
                auction.setKickback(bitToBool(rs.getInt("kickedBack")));
                auction.setDenied(bitToBool(rs.getInt("denied")));
            }
        }catch (SQLException e){
            System.out.println("SQL error retrieving auction by ID");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auction;
    }

    public static ArrayList<Auction> getPendingAuctionsByDate(){
        Connection con = DBConnection.getConnection();
        ArrayList<Auction> auctions = new ArrayList<>();
        String query = "SELECT * FROM Auction WHERE approvalStatus=0 AND kickedBack=0 AND denied=0 ORDER BY dateTimeCreated";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

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
                auction.setFixed(bitToBool(rs.getInt("fixedAuction")));
                auction.setDescription(rs.getString("description"));
                auction.setAuctionImages(AuctionImageDAO.getAuctionImages(auction.getAuctionID()));
                auction.setKeywords(AuctionKeywordDAO.getAuctionKeywords(auction.getAuctionID()));
                auction.setKickback(bitToBool(rs.getInt("kickedBack")));
                auction.setDenied(bitToBool(rs.getInt("denied")));
                auctions.add(auction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auctions;
    }

    public static boolean confirmAuction(Auction auction){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        String query = "UPDATE auction SET livestatus=1, approvalStatus=1, dateTimeConfirmed=NOW() WHERE auctionID=?";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, auction.getAuctionID());
            statement.executeUpdate();
            truthFlag=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return truthFlag;
    }

    public static boolean denyAuction(Auction auction){
        Connection con = DBConnection.getConnection();
        boolean truthFlag = false;
        String query = "UPDATE auction SET denied=1 WHERE auctionID=?";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, auction.getAuctionID());
            statement.executeUpdate();
            truthFlag=true;
        } catch (SQLException e) {
            e.printStackTrace();
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
