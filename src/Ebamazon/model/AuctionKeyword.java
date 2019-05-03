package Ebamazon.model;

import java.util.ArrayList;

public class AuctionKeyword {
    private Auction auction;
    private ArrayList<String> keywords;

    //Getters and Setters
    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }
}
