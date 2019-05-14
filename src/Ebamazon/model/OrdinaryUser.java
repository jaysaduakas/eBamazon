package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OrdinaryUser extends User{
    private String address;
    private String stateID;
    private String cc;
    private String phone;
    private boolean approvedStatus;
    private boolean bannedStatus;
    private boolean VIPStatus;
    private Timestamp dateTimeRegistered;

    public OrdinaryUser(){
        setUserStatus(UserStatus.OU);
    }

    //utility functions
    public boolean checkIfUsernameIsPW(){return OrdinaryUserDAO.checkIfPasswordIsUsername(getUsername());}

    //edit user info functions
    public boolean updateUserInfo(){
        return OrdinaryUserDAO.setUser(this);
    }
    public boolean changeUserPW(String pw){
        return OrdinaryUserDAO.setUserPassword(this, pw);
    }
    public boolean insertUserInfo(){
        //if user with object's username does not exist in the OU table, insert it with the values specified by
        //this object.
        if (!OrdinaryUserDAO.checkExists(getUsername())  && !SuperUserDAO.checkExists(getUsername()))  {
            return OrdinaryUserDAO.submitNewUser(this) && PendingApplicationDAO.insertNewUser(getUsername());
        }
        return false;
    }

    //user keyword functions
    public void submitKeyword(UserKeyword kw){
        UserKeywordDAO.submitKeyword(kw);
    }
    public ArrayList<UserKeyword> getKeywords(){
        return UserKeywordDAO.getKeywords(getUsername());
    }
    public boolean deleteKeyword(UserKeyword uk){ return UserKeywordDAO.deleteKeyword(uk);}

    //user auction functions
    public void submitAuction(Auction auction){
        AuctionDAO.insertAuction(auction);
    }
    public ArrayList<Auction> getMyAuctions() throws SQLException { return AuctionDAO.getAuctionsByUsername(this.getUsername());}

    //friend functions
    public boolean makeFriendRequest(Friends friend) {return FriendDAO.insertFriendRequest(friend);}
    public boolean confirmFriend(Friends friends) {return FriendDAO.confirmFriendRequest(friends);}
    public boolean deleteFriend(Friends friends) {return FriendDAO.deleteFriend(friends);}
    public ArrayList<Friends> getFriends() { return FriendDAO.getConfirmedFriends(getUsername());}
    public ArrayList<Friends> getFriendRequests() {return FriendDAO.getFriendRequests(getUsername());}

    //bid functions
    public boolean makeBid(Bid bid) {return BidDAO.makeBidOnAuction(bid);}
    public ArrayList<Bid> getWinningsBids() { return BidDAO.getWinningBids(getUsername());}
    public boolean declareWinningBid(Bid bid) { return BidDAO.declareWinningBid(bid);}

    //getters and setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(boolean approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public boolean isBannedStatus() {
        return bannedStatus;
    }

    public void setBannedStatus(boolean bannedStatus) {
        this.bannedStatus = bannedStatus;
    }

    public boolean isVIPStatus() {
        return VIPStatus;
    }

    public void setVIPStatus(boolean VIPStatus) {
        this.VIPStatus = VIPStatus;
    }

    public Timestamp getDateTimeRegistered() {
        return dateTimeRegistered;
    }

    public void setDateTimeRegistered(Timestamp dateTimeRegistered) {
        this.dateTimeRegistered = dateTimeRegistered;
    }

    @Override
    public String toString() {
        return "OrdinaryUser{" +
                "username='" + getUsername() + '\'' +
                ", name='" + getName() + '\'' +
                ", address='" + address + '\'' +
                ", stateID='" + stateID + '\'' +
                ", cc='" + cc + '\'' +
                ", phone='" + phone + '\'' +
                ", approvedStatus=" + approvedStatus +
                ", bannedStatus=" + bannedStatus +
                ", VIPStatus=" + VIPStatus +
                ", dateTimeRegistered=" + dateTimeRegistered +
                '}';
    }
}
