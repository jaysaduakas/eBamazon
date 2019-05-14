package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.Auction;
import Ebamazon.model.Bid;
import Ebamazon.model.CurrentSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BidComponentViewController {
    private Bid bid;
    private Auction auction;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox viewComponent;

    @FXML
    private Label title;

    @FXML
    private Label date;

    @FXML
    private Label seller;

    @FXML
    private Label price;

    @FXML
    void initialize() {
        assert viewComponent != null : "fx:id=\"viewComponent\" was not injected: check your FXML file 'bidComponentView.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'bidComponentView.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'bidComponentView.fxml'.";
        assert seller != null : "fx:id=\"seller\" was not injected: check your FXML file 'bidComponentView.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'bidComponentView.fxml'.";

    }

    public void setUpBidComponent(){
        if(auction != null && bid != null) {
            title.setText(auction.getTitle());
            date.setText(auction.getDateTimeConfirmed().toString());
            seller.setText(auction.getOrdinaryUser().getUsername());
            price.setText(auction.getPrice().toString());
        }

    }

    public VBox getViewComponent() {
        return viewComponent;
    }

    public void setViewComponent(VBox viewComponent) {
        this.viewComponent = viewComponent;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
