package Ebamazon.model;


import Ebamazon.model.DataAccessLayer.AuctionDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrentSession {

    private User curUser;
    private boolean isBanned;
    //is suspended
    //has kickback auctions
    private UserStatus userStatus;
    private InputScrubber inputScrubber;
    private ArrayList<AuctionResult> currentSearchResults;
    private boolean sortByRelevance=true; // if false it implies sorting by seller rating


    public CurrentSession(){
        curUser = new User();
        isBanned = false;
        userStatus = UserStatus.GU;
    }

    public CurrentSession(User user){
        setCurUser(user);
    }

    public void setCurUser(User user){
        curUser = user;
        if (user instanceof OrdinaryUser){
            userStatus = UserStatus.OU;
            isBanned = ((OrdinaryUser) user).isBannedStatus();
        }
        else if (user instanceof SuperUser){
            userStatus = UserStatus.SU;
            isBanned = false;
        }
        else {
            userStatus = UserStatus.GU;
            isBanned = false;
        }
    }

    public ArrayList<AuctionResult> generateSearchResults(SearchParameters sp) throws SQLException {
        currentSearchResults = AuctionDAO.getAuctionsByParameter(sp);
        sortSearchResults();
        return currentSearchResults;
    }

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

    public boolean isSortByRelevance() {
        return sortByRelevance;
    }

    public void setSortByRelevance(boolean sortByRelevance) {
        this.sortByRelevance = sortByRelevance;
        sortSearchResults();
    }

    private void sortSearchResults(){
        if(isSortByRelevance()){
            Collections.sort((List)currentSearchResults);
        }
        else{
            //TODO insert sorting by user rating
        }
    }

    public static void main(String[] args) throws SQLException {
        CurrentSession cs = new CurrentSession();
        SearchParameters sp = new SearchParameters("big glass table");
        cs.generateSearchResults(sp);
        cs.setSortByRelevance(true);
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
