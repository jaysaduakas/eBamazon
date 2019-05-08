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
    private boolean fixedOrBid;
    private ArrayList<AuctionKeyword> keywords;
    private ArrayList<AuctionImage> auctionImages;
    private String description;
    private boolean liveStatus;

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

    public boolean isFixedOrBid() {
        return fixedOrBid;
    }

    public void setFixedOrBid(boolean fixedOrBid) {
        this.fixedOrBid = fixedOrBid;
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

    @Override //Dont have ArrayLists of images and keywords include in this toString method.
    public String toString() {
        return "Auction{" +
                "auctionID='" + auctionID + '\'' +
                ", title='" + title + '\'' +
                ", ordinaryUser='" + ordinaryUser + '\'' +
                ", dateTimeCreated='" + dateTimeCreated + '\'' +
                ", dateTimeConfirmed='" + dateTimeConfirmed + '\'' +
                ", price='" + price + '\'' +
                ", fixedOrBid=" + fixedOrBid +
                ", description=" + description +
                ", liveStatus=" + liveStatus +
                '}';
    }
}
