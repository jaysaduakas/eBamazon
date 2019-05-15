package Ebamazon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.Auction;
import Ebamazon.model.Bid;
import Ebamazon.model.CurrentSession;
import Ebamazon.model.OrdinaryUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SellItemViewController {

    private CurrentSession currentSession;
    private BorderPane parent;
    private ToggleGroup tg;
    private Auction auction;
    private Bid selectedBid;
    private ArrayList<Bid> bids;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox scrollableVBox;

    @FXML
    private VBox mainVBox;

    @FXML
    private TextArea justifyBox;

    @FXML
    private Button sellButton;

    @FXML
    private Button backButton;

    @FXML
    private Label warning;

    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader myAuctionViewLoader = new FXMLLoader();
        myAuctionViewLoader.setLocation(getClass().getResource("../view/myAuctionsView.fxml"));
        AnchorPane view = myAuctionViewLoader.load();
        MyAuctionsViewController mavc = myAuctionViewLoader.getController();
        parent.setCenter(view);
        mavc.setCurrentSession(currentSession);
        mavc.setParent(parent);
        System.out.println(((OrdinaryUser)currentSession.getCurUser()).toString());
        mavc.populateAuctionList((OrdinaryUser)currentSession.getCurUser());
    }

    @FXML
    void sell(ActionEvent event) {
        if (selectedBid!=null){
            ((OrdinaryUser)currentSession.getCurUser()).declareWinningBid(selectedBid);
            auction.endAuction();
            //CHECK FOR VIP STUFF
        }
    }

    @FXML
    void initialize() {
        assert scrollableVBox != null : "fx:id=\"scrollableVBox\" was not injected: check your FXML file 'sellItemView.fxml'.";
        assert mainVBox != null : "fx:id=\"mainVBox\" was not injected: check your FXML file 'sellItemView.fxml'.";
        assert justifyBox != null : "fx:id=\"justifyBox\" was not injected: check your FXML file 'sellItemView.fxml'.";
        assert sellButton != null : "fx:id=\"sellButton\" was not injected: check your FXML file 'sellItemView.fxml'.";
        tg = new ToggleGroup();
    }


    private void attachBid(Bid bid) throws IOException {
        FXMLLoader componentLoader = new FXMLLoader();
        componentLoader.setLocation(getClass().getResource("../view/sellableBidComponentView.fxml"));
        VBox view = componentLoader.load();
        SellableBidComponentViewController sbcvc = componentLoader.getController();
        sbcvc.getSellRadioButton().setToggleGroup(tg);
        sbcvc.setAuction(auction);
        sbcvc.setBid(bid);
        sbcvc.setParent(this);
        if (auction.isFixed()) {
            if (sbcvc.getBid() == bids.get(0)){
                sbcvc.getSellRadioButton().setSelected(true);
                selectedBid = bid;
            }
        } else {
            if (bids.size() >= 2) {
                if (sbcvc.getBid() == bids.get(1)) {
                    sbcvc.getSellRadioButton().setSelected(true);
                    selectedBid = bid;
                }
            }
        }
        scrollableVBox.getChildren().add(view);
    }



    //getters and setters

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
        bids = new ArrayList<>();
        if (auction.isFixed()){
            bids = currentSession.getBidsForAuctionByDte(auction);
        }else{
            bids = currentSession.getBidsForAuctionByAmount(auction);
        }
        for (Bid bid : bids){
            try {
                attachBid(bid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Bid getSelectedBid() {
        return selectedBid;
    }

    public void setSelectedBid(Bid selectedBid) {
        this.selectedBid = selectedBid;
    }
}
