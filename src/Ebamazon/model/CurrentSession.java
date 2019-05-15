package Ebamazon.model;


import Ebamazon.model.DataAccessLayer.*;

import java.sql.SQLException;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrentSession {

    private User curUser;
    private boolean isBanned;
    //is suspended
    private boolean isSuspended;
    //has kickback auctions
    private boolean hasKickBackAuctions;
    private boolean hasComplaints;
    private UserStatus userStatus;
    private InputScrubber inputScrubber;
    private ArrayList<AuctionResult> currentSearchResults;
    private ArrayList<String> friendsUsernames;
    private boolean sortByRelevance;
    private double taxRate;

    private ArrayList<Complaint> complaints;

    //constructors
    public CurrentSession() {
        curUser = new User();
        isBanned = false;
        userStatus = UserStatus.GU;
        friendsUsernames = new ArrayList<>();
    }
    public CurrentSession(User user) {
        setCurUser(user);
        friendsUsernames = new ArrayList<>();
    }

    //set the current user
    public void setCurUser(User user) {
        curUser = user;
        if (user instanceof OrdinaryUser) {
            userStatus = UserStatus.OU;
            isBanned = ((OrdinaryUser) user).isBannedStatus();
            updateUserFriends();
            setTaxRate();
            checkForComplaints();
        }
       else if (user instanceof SuperUser) {
            userStatus = UserStatus.SU;
            isBanned = false;
        } else {
            userStatus = UserStatus.GU;
            isBanned = false;
        }
    }

    //Ordinary User Functions
    public ArrayList<AuctionResult> generateSearchResults(SearchParameters sp) throws SQLException {
        currentSearchResults = AuctionDAO.getAuctionsByParameter(sp);
        //sortSearchResults();
        for (AuctionResult ar : currentSearchResults){
            ar.setSwapValue();
        }
        Collections.sort(currentSearchResults);
        setSortByRelevance(true);
        return currentSearchResults;
    }

    public OrdinaryUser getUserByUsername(String username) {
        return OrdinaryUserDAO.getOrdinaryUser(username);
    }

    public void updateUserFriends() {
        friendsUsernames.clear();
        for (Friends f : ((OrdinaryUser) curUser).getFriends()) {
            if (f.getConfirmingFriendID().equals(curUser.getUsername())) {
                friendsUsernames.add(f.getSuggestingFriendlD());
            } else {
                friendsUsernames.add(f.getConfirmingFriendID());
            }
        }
    }
    public ArrayList<Bid> getBidsForAuction(Auction auction) {return BidDAO.getBidsForAuction(auction.getAuctionID());}
    public ArrayList<Bid> getBidsForAuctionByAmount(Auction auction) {return BidDAO.getBidsForAuctionOrderedByAmount(auction.getAuctionID());}
    public ArrayList<Bid> getBidsForAuctionByDte(Auction auction) {return BidDAO.getBidsForAuctionOrderedByDate(auction.getAuctionID());}

    public void setTaxRate(){
        taxRate = TaxDAO.getTaxRate(((OrdinaryUser)curUser).getStateID());
    }

    //complaint handling functions
    private void checkForComplaints(){
        complaints = ComplaintDAO.getComplaineeComplaints(curUser.getUsername());
        if (!complaints.isEmpty()) hasComplaints = true;
    }
    public boolean updateComplaint(Complaint c) {return ComplaintDAO.updateComplaint(c);}

    //Super User Functions
    public boolean insertTaboo(Taboo taboo){return TabooDAO.insertTaboo(taboo);}
    public ArrayList<String> getAllTaboos() throws SQLException {return TabooDAO.getTabooWords();}
    public boolean deleteTaboo(String taboo) {return TabooDAO.deleteTaboo(taboo);}

    //Utility FUnction
    public Auction getAuctionByID(int id) {return AuctionDAO.getAuctionByID(id);}

    public void sortSearchResults(){
        for (AuctionResult ar : currentSearchResults){
            ar.swapScores();
        }
        Collections.sort(currentSearchResults);
    }


    //getters and setters
    public User getCurUser(){
        return curUser;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public InputScrubber getInputScrubber() {
        return inputScrubber;
    }

    public void setInputScrubber(InputScrubber inputScrubber) {
        this.inputScrubber = inputScrubber;
    }

    public ArrayList<AuctionResult> getCurrentSearchResults() {
        return currentSearchResults;
    }

    public void setCurrentSearchResults(ArrayList<AuctionResult> currentSearchResults) {
        this.currentSearchResults = currentSearchResults;
    }


    public ArrayList<String> getFriendsUsernames() {
        return friendsUsernames;
    }

    public void setFriendsUsernames(ArrayList<String> friendsUsernames) {
        this.friendsUsernames = friendsUsernames;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public boolean isHasKickBackAuctions() {
        return hasKickBackAuctions;
    }

    public void setHasKickBackAuctions(boolean hasKickBackAuctions) {
        this.hasKickBackAuctions = hasKickBackAuctions;
    }

    public boolean isHasComplaints() {
        return hasComplaints;
    }

    public void setHasComplaints(boolean hasComplaints) {
        this.hasComplaints = hasComplaints;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public ArrayList<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(ArrayList<Complaint> complaints) {
        this.complaints = complaints;
    }

    public boolean isSortByRelevance() {
        return sortByRelevance;
    }

    public void setSortByRelevance(boolean sortByRelevance) {
        this.sortByRelevance = sortByRelevance;
    }

    public static void main(String[] args) throws SQLException {
        CurrentSession cs = new CurrentSession();
        SearchParameters sp = new SearchParameters("big glass table");
        cs.generateSearchResults(sp);
        for (AuctionResult ar: cs.getCurrentSearchResults()){
            System.out.println(ar.getAuctionID() + " " + ar.getScore());
        }
        cs.sortSearchResults();
        System.out.println();
        for (AuctionResult ar: cs.getCurrentSearchResults()){
            System.out.println(ar.getAuctionID() + " " + ar.getScore());
        }
    }

}
