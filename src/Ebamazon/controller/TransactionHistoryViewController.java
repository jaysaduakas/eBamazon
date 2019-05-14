package Ebamazon.controller;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

public class TransactionHistoryViewController {

    private CurrentSession currentSession;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox bidComponentVBox;

    @FXML
    private VBox bidSoldComponentVBox1;

    @FXML
    void initialize() {
        assert bidComponentVBox != null : "fx:id=\"bidComponentVBox\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert bidSoldComponentVBox1 != null : "fx:id=\"bidSoldComponentVBox1\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
        setUpBuys();
        setUpSales();
    }

    private void attachNewBidView(Bid bid, Auction a) {
        FXMLLoader bidComponentLoader = new FXMLLoader();
        bidComponentLoader.setLocation(getClass().getResource("../view/bidComponentView.fxml"));
        try {
            VBox auctionComponent = bidComponentLoader.load();
            bidComponentVBox.getChildren().add(auctionComponent);
        } catch (Exception e) {
            System.out.println("bid component controller not loaded");
        }
        BidComponentViewController bcvc = bidComponentLoader.getController();
        bcvc.setAuction(a);
        bcvc.setBid(bid);
        bcvc.setUpBidComponent();
    }

    private void setUpBuys() {
        ArrayList<Bid> bids = ((OrdinaryUser) currentSession.getCurUser()).getWinningsBids();
        for (Bid b : bids) {
            Auction auction = currentSession.getAuctionByID(b.getAuction().getAuctionID());
            attachNewBidView(b, auction);
        }
    }
    private void setUpSales(){
        ArrayList<Auction> auctions = ((OrdinaryUser)currentSession.getCurUser()).getMyAuctions();
        for (Auction a: auctions){
            ArrayList<Bid> bs= currentSession.getBidsForAuction(a);
            for(Bid b: bs){
                if(b.isWinningBid()){
                    attachNewSaleView(b, a);
                }
            }
        }

    }
    private void attachNewSaleView(Bid bid, Auction a) {
        FXMLLoader bidSaleComponentLoader = new FXMLLoader();
        bidSaleComponentLoader.setLocation(getClass().getResource("../view/bidSaleComponentView.fxml"));
        try {
            VBox auctionComponent = bidSaleComponentLoader.load();
            bidSoldComponentVBox1.getChildren().add(auctionComponent);
        } catch (Exception e) {
            System.out.println("bid component controller not loaded");
        }
        BidSaleComponentViewController bscvc = bidSaleComponentLoader.getController();
        bscvc.setAuction(a);
        bscvc.setBid(bid);
        bscvc.setUpBidSaleComponent();

    }




}

