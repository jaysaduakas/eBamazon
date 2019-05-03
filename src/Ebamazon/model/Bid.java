package Ebamazon.model;

import java.sql.Timestamp;

public class Bid {
    private Auction auction;
    private double amount;
    private boolean winningBid;
    private Timestamp dateTimeMade;
    private OrdinaryUser ordinaryUser;

    //Getters and Setters

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isWinningBid() {
        return winningBid;
    }

    public void setWinningBid(boolean winningBid) {
        this.winningBid = winningBid;
    }

    public Timestamp getDateTimeMade() {
        return dateTimeMade;
    }

    public void setDateTimeMade(Timestamp dateTimeMade) {
        this.dateTimeMade = dateTimeMade;
    }

    public OrdinaryUser getOrdinaryUser() {
        return ordinaryUser;
    }

    public void setOrdinaryUser(OrdinaryUser ordinaryUser) {
        this.ordinaryUser = ordinaryUser;
    }
}
