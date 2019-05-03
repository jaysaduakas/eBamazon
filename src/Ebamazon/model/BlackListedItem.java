package Ebamazon.model;

import java.sql.Timestamp;

public class BlackListedItem {
    // Instance variables
    private Auction auction;
    private SuperUser superUser;
    private String reason;
    private Timestamp dateTimeBlackListed;

    // Getters and setters


    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public SuperUser getSuperUser() {
        return superUser;
    }

    public void setSuperUser(SuperUser superUser) {
        this.superUser = superUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getDateTimeBlackListed() {
        return dateTimeBlackListed;
    }

    public void setDateTimeBlackListed(Timestamp dateTimeBlackListed) {
        this.dateTimeBlackListed = dateTimeBlackListed;
    }


    // TODO: Instance methods


    @Override
    public String toString() {
        return "BlackListedItem{" +
                "auctionr='" + auction.getAuctionID() + '\'' +
                ", superUser='" + superUser.getUsername() + '\'' +
                ", reason='" + reason + '\'' +
                ", dateTimeBlackListed='" + dateTimeBlackListed + '\'' +
                '}';
    }
}
