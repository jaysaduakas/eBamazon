package Ebamazon.model;

import javafx.scene.image.Image;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Auction {

    private int auctionID;
    private String title;
    private boolean approvalStatus;
    private OrdinaryUser ordinaryUser;
    private Timestamp dateTimeCreated;
    private Timestamp dateTimeConfirmed;
    private BigDecimal price;
    private boolean fixed;
    private ArrayList<AuctionKeyword> keywords;
    private ArrayList<AuctionImage> auctionImages;
    private String description;
    private boolean liveStatus;
    private boolean kickback;



    //Getters and Setters
    public int getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public OrdinaryUser getOrdinaryUser() {
        return ordinaryUser;
    }

    public void setOrdinaryUser(OrdinaryUser ordinaryUser) {
        this.ordinaryUser = ordinaryUser;
    }

    public Timestamp getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Timestamp dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Timestamp getDateTimeConfirmed() {
        return dateTimeConfirmed;
    }

    public void setDateTimeConfirmed(Timestamp dateTimeConfirmed) {
        this.dateTimeConfirmed = dateTimeConfirmed;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.DOWN);;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixedOrBid) {
        this.fixed = fixedOrBid;
    }

    public ArrayList<AuctionKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<AuctionKeyword> keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(boolean liveStatus) {
        this.liveStatus = liveStatus;
    }

    public ArrayList<AuctionImage> getAuctionImages() {
        return auctionImages;
    }

    public void setAuctionImages(ArrayList<AuctionImage> auctionImages) {
        this.auctionImages = auctionImages;
    }

    public boolean isKickback() {
        return kickback;
    }

    public void setKickback(boolean kickback) {
        this.kickback = kickback;
    }

    public void confirmSale(Bid b){
        Message m1 = new Message();
        m1.setMessageContent("New Bid! Please check your auctions");
        Message m2 = new Message();
        m2.setMessageContent("You are the winner of an auction! Your credit card has been charged. Check your inbox!");

        //prompt ordinary user to accept
        ordinaryUser.sendMessage(m1);

        if (fixed && approvalStatus){ //if sale is fixed and seller approves
            ordinaryUser.declareWinningBid(b);
            ordinaryUser.sendMessage(m2);
        }
        else if(fixed && !approvalStatus){ //if sale is fixed and seller denies
            //insert justification into table
            setLiveStatus(true);
        }
        else if (!fixed && approvalStatus){ // sale is auction and seller approves (2nd highest wins)
            //get 2nd highest bid
            ordinaryUser.declareWinningBid(b);
            ordinaryUser.sendMessage(m2);
        }
        else{   // sale is auction and seller denies
            //insert justification in table
            setLiveStatus(true);
        }
    }

    @Override //Don't have ArrayLists of images and keywords include in this toString method.
    public String toString() {
        return "Auction{" +
                "auctionID='" + auctionID + '\'' +
                ", title='" + title + '\'' +
                ", ordinaryUser='" + ordinaryUser + '\'' +
                ", dateTimeCreated='" + dateTimeCreated + '\'' +
                ", dateTimeConfirmed='" + dateTimeConfirmed + '\'' +
                ", price='" + price + '\'' +
                ", fixedOrBid=" + fixed +
                ", description=" + description +
                ", liveStatus=" + liveStatus +
                '}';
    }
}
