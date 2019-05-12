package Ebamazon.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Bid {
    private Auction auction;
    private BigDecimal amount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public void submitBids(Auction a, Bid b){
        //if no discount
        if (a.isLiveStatus()) { //if live, insert bid
            a.getOrdinaryUser().makeBid(b);
        }
        if (a.isFixedOrBid()) { //if sale is fixed
            a.setLiveStatus(false);
            a.confirmSale(b);
        }
        //if bid amount == max price --> close auction
        else if(a.getPrice() == b.getAmount()){ //if sale is auction and at max price
            a.setLiveStatus(false);
            a.confirmSale(b);
        }
        else{
            a.confirmSale(b);
        }
    }


    @Override
    public String toString() {
        return "Bid{" +
                "auction=" + auction +
                ", amount=" + amount +
                ", winningBid=" + winningBid +
                ", dateTimeMade=" + dateTimeMade +
                ", ordinaryUser=" + ordinaryUser +
                '}';
    }
}
