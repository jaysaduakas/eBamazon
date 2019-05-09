package Ebamazon.model;


import java.util.ArrayList;

public class CurrentSession {

    private User curUser;
    private boolean isBanned;
    //is suspended
    //has kickback auctions
    private UserStatus userStatus;
    private InputScrubber inputScrubber;
    private ArrayList<Auction> currentSearchResults;
    private boolean sortByRelevance; // if false it implies sorting by seller rating


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

    public void generateSearchResults(SearchParameters sp){
        //do the work
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

    public ArrayList<Auction> getCurrentSearchResults() {
        return currentSearchResults;
    }

    public void setCurrentSearchResults(ArrayList<Auction> currentSearchResults) {
        this.currentSearchResults = currentSearchResults;
    }

    public boolean isSortByRelevance() {
        return sortByRelevance;
    }

    public void setSortByRelevance(boolean sortByRelevance) {
        this.sortByRelevance = sortByRelevance;
    }
}
