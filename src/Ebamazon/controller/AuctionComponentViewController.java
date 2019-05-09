package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.Auction;
import Ebamazon.model.AuctionImage;
import Ebamazon.model.AuctionKeyword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AuctionComponentViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane auctionComponent;

    @FXML
    private ImageView auctionImage;

    @FXML
    private Label title;

    @FXML
    private Label creator;

    @FXML
    private Label date;

    @FXML
    private Label desc;

    @FXML
    private Label keywords;

    @FXML
    private TextField bidBox;

    @FXML
    private Button bidButton;

    @FXML
    private Label price;

    @FXML
    void bid(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert auctionComponent != null : "fx:id=\"auctionComponent\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert auctionImage != null : "fx:id=\"auctionImage\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert creator != null : "fx:id=\"creator\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert desc != null : "fx:id=\"desc\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert keywords != null : "fx:id=\"keywords\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert bidBox != null : "fx:id=\"bidBox\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert bidButton != null : "fx:id=\"bidButton\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'auctionComponentView.fxml'.";

    }

    public void updateDisplayImage(Image image){
        auctionImage.setImage(image);
    }

    public AnchorPane getAuctionComponent() {
        return auctionComponent;
    }

    public ImageView getAuctionImage() {
        return auctionImage;
    }

    public Label getTitle() {
        return title;
    }

    public Label getCreator() {
        return creator;
    }

    public Label getDate() {
        return date;
    }

    public Label getDesc() {
        return desc;
    }

    public Label getKeywords() {
        return keywords;
    }

    public TextField getBidBox() {
        return bidBox;
    }

    public Button getBidButton() {
        return bidButton;
    }

    public Label getPrice() {
        return price;
    }

    public void setUpAuction(Auction a){
        getTitle().setText(a.getTitle());
        getCreator().setText(a.getOrdinaryUser().getUsername());
        getDesc().setText(a.getDescription());
        if (a.getDateTimeConfirmed()!=null){
            getDate().setText(a.getDateTimeConfirmed().toString());
        }else{
            getDate().setText("Auction Is Not Yet Confirmed by SuperUser");
        }
        getPrice().setText("Price: " + a.getPrice());
        Image image = null;
        for (AuctionImage i : a.getAuctionImages()){
            if (i.isDefaultPhoto()){
                image = i.getImage();
            }
        }
        String keywords = "";
        for (AuctionKeyword k : a.getKeywords()){
            keywords+= k.getKeyword() + ", ";
        }
        keywords = keywords.substring(0, keywords.length()-2);
        getKeywords().setText(keywords);
        updateDisplayImage(image);
    }

}

