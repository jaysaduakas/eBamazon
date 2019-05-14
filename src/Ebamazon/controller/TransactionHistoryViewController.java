package Ebamazon.controller;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
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
    private Button searchHistory;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;

    @FXML
    private VBox bidComponentVBox;

    @FXML
    void updateTransactions(ActionEvent event) {
        setUpTransactionHistory();
    }

    @FXML
    void initialize() {
        assert searchHistory != null : "fx:id=\"searchHistory\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert fromDate != null : "fx:id=\"fromDate\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert toDate != null : "fx:id=\"toDate\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert bidComponentVBox != null : "fx:id=\"bidComponentVBox\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";

    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }

    private void attachNewBidView(Bid bid) {
        FXMLLoader bidComponentLoader = new FXMLLoader();
        bidComponentLoader.setLocation(getClass().getResource("../view/bidComponentView.fxml"));
        try {
            VBox auctionComponent = bidComponentLoader.load();
            bidComponentVBox.getChildren().add(auctionComponent);
        } catch (Exception e) {
            System.out.println("bid component controller not loaded");
        }
        BidComponentViewController bcvc = bidComponentLoader.getController();
        Auction auction = currentSession.getAuctionByID(bid.getAuction().getAuctionID());
        bcvc.setAuction(auction);
        bcvc.setBid(bid);
        bcvc.setUpBidComponent();
    }

    private void setUpTransactionHistory() {
        //This is kinda broken fam
        //It's a timeformat issue cuz it's needs to be in Timestamp form mofo
        //Format ldFrom and ldTo into yyyy-mm-dd hh:mm:ss[.fffffffff]
        LocalDate localFromDate = fromDate.getValue();
        Instant instant = Instant.from(localFromDate.atStartOfDay(ZoneId.systemDefault()));
        Date ldFrom = Date.from(instant);
        Date ldTo = Date.from(instant);
        Timestamp tsFromDate =  Timestamp.valueOf(String.valueOf(ldFrom));
        Timestamp tsToDate =  Timestamp.valueOf(String.valueOf(ldTo));
        ArrayList<Bid> bids = ((OrdinaryUser) currentSession.getCurUser()).getWinningsBids();
        for (Bid b : bids) {
            Auction auction = currentSession.getAuctionByID(b.getAuction().getAuctionID());
            if (auction.getDateTimeConfirmed().after(tsFromDate) && auction.getDateTimeConfirmed().before(tsToDate)) {//date time between from date and to date)
                attachNewBidView(b);
            }
        }
    }




}

