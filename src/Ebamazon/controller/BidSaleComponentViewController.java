package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.Auction;
import Ebamazon.model.Bid;
import Ebamazon.model.CurrentSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BidSaleComponentViewController {

    private Auction auction;
    private Bid bid;

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
    private Label seller; //actually its buyer

    @FXML
    private Label price;

    @FXML
    void initialize() {
        assert viewComponent != null : "fx:id=\"viewComponent\" was not injected: check your FXML file 'BidSaleComponentView.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'BidSaleComponentView.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'BidSaleComponentView.fxml'.";
        assert seller != null : "fx:id=\"seller\" was not injected: check your FXML file 'BidSaleComponentView.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'BidSaleComponentView.fxml'.";

    }
    public void setUpBidSaleComponent(){
        if(auction != null && bid != null) {
            title.setText(auction.getTitle());
            date.setText(bid.getDateTimeMade().toString());
            seller.setText(bid.getOrdinaryUser().getUsername()); //really the buyer, not the seller
            Double da = Double.parseDouble(auction.getPrice().toString());
            Double db = Double.parseDouble(bid.getAmount().toString());
            price.setText(Double.toString(Double.max(da,db)));
        }

    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }
}
