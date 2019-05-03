package Ebamazon.model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class AuctionImage {
    private Auction auction;
    private ArrayList<Image> image;

    //Getters and Setters
    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }
}
