package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.Auction;
import Ebamazon.model.Bid;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

public class SellableBidComponentViewController {

    private Bid bid;
    private Auction auction;
    private SellItemViewController parent;

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

    //BIDDER
    @FXML
    private Label seller;

    @FXML
    private Label price;

    @FXML
    private RadioButton sellRadioButton;

    @FXML
    void select(ActionEvent event) {
        parent.setSelectedBid(bid);
    }



    @FXML
    void initialize() {
        assert viewComponent != null : "fx:id=\"viewComponent\" was not injected: check your FXML file 'sellableBidComponentView.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'sellableBidComponentView.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'sellableBidComponentView.fxml'.";
        assert seller != null : "fx:id=\"seller\" was not injected: check your FXML file 'sellableBidComponentView.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'sellableBidComponentView.fxml'.";
        assert sellRadioButton != null : "fx:id=\"sellRadioButton\" was not injected: check your FXML file 'sellableBidComponentView.fxml'.";

    }

    public RadioButton getSellRadioButton() {
        return sellRadioButton;
    }

    public void setSellRadioButton(RadioButton sellRadioButton) {
        this.sellRadioButton = sellRadioButton;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
        date.setText(bid.getDateTimeMade().toString());
        seller.setText(bid.getOrdinaryUser().getUsername());
        double da = Double.parseDouble(bid.getAmount().toString());
        double db = Double.parseDouble(auction.getPrice().toString());
        price.setText(Double.toString(Double.max(da,db)));
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public SellItemViewController getParent() {
        return parent;
    }

    public void setParent(SellItemViewController parent) {
        this.parent = parent;
    }
}
