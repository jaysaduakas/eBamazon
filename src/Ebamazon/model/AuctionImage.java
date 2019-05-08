package Ebamazon.model;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public class AuctionImage {
    private int auction;
    private int imageNumber;
    private File image;
    private boolean defaultPhoto;

    //Getters and Setters
    public int getAuction() {
        return auction;
    }

    public void setAuction(int auction) {
        this.auction = auction;
    }

    public Image getImage() {
        return new Image(image.toURI().toString());
    }

    public File getImageFile(){ return image;}

    public void setImage(File image) {
        this.image = image;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    public boolean isDefaultPhoto() {
        return defaultPhoto;
    }

    public void setDefaultPhoto(boolean defaultPhoto) {
        this.defaultPhoto = defaultPhoto;
    }

    @Override
    public String toString() {
        return "AuctionImage{" +
                "auction=" + auction +
                ", imageNumber=" + imageNumber +
                ", image=" + image +
                ", defaultPhoto=" + defaultPhoto +
                '}';
    }
}
