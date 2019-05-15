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

import Ebamazon.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class TransactionHistoryViewController {

    private CurrentSession currentSession;
    private BidComponentViewController curController;
    private ToggleGroup gradingToggle;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox bidComponentVBox;

    @FXML
    private VBox bidSoldComponentVBox1;

    @FXML
    private VBox itemsSoldVBox;

    @FXML
    private VBox gradeVBox;

    @FXML
    private RadioButton button0;

    @FXML
    private RadioButton button1;

    @FXML
    private RadioButton button2;

    @FXML
    private RadioButton button3;

    @FXML
    private RadioButton button4;

    @FXML
    private RadioButton button5;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    void closeGrading(ActionEvent event){
        hideGradingShowSales();
    }

    @FXML
    void gradeUser(ActionEvent event) {
        Ratings r = new Ratings();
        insertRating(r);
        hideGradingShowSales();
        curController.hideGradeButton();
        checkToWarnGradee(r);    //call function to check if warning needs to send to gradee
        checkToWarnGrader(r);    //call function to check if need to yell at grader
        checkToUpdateVIP(r);     //call function to update vip status
    }

    @FXML
    void initialize() {
        assert bidComponentVBox != null : "fx:id=\"bidComponentVBox\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert itemsSoldVBox != null : "fx:id=\"itemsSoldVBox\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert bidSoldComponentVBox1 != null : "fx:id=\"bidSoldComponentVBox1\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert gradeVBox != null : "fx:id=\"gradeVBox\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert button0 != null : "fx:id=\"button0\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert button2 != null : "fx:id=\"button2\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert button3 != null : "fx:id=\"button3\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert button4 != null : "fx:id=\"button4\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert button5 != null : "fx:id=\"button5\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'transactionHistoryView.fxml'.";

        gradingToggle = new ToggleGroup();
        button0.setToggleGroup(gradingToggle);
        button1.setToggleGroup(gradingToggle);
        button2.setToggleGroup(gradingToggle);
        button3.setToggleGroup(gradingToggle);
        button4.setToggleGroup(gradingToggle);
        button5.setToggleGroup(gradingToggle);
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
        curController = bcvc;
        bcvc.setAuction(a);
        bcvc.setBid(bid);
        bcvc.setUpBidComponent();
        bcvc.setParent(this);
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

    private void insertRating(Ratings ratings){
        ratings.setRatee(curController.getAuction().getOrdinaryUser().getUsername());
        ratings.setRater(currentSession.getCurUser().getUsername());
        ratings.setRating(getRatingValue());
        ratings.setAuctionID(curController.getAuction().getAuctionID());
        ratings.insertRating();
    }

    private double getRatingValue(){
        if (button0.isSelected()){
            return 0.0;
        }
        else if (button1.isSelected()){
            return 1.0;
        }
        else if (button2.isSelected()){
            return 2.0;
        }
        else if (button3.isSelected()){
            return 3.0;
        }
        else if (button4.isSelected()){
            return 4.0;
        }
        else{
            return 5.0;
        }
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


    public void hideSaleShowGrading(BidComponentViewController bcvc){
        curController = bcvc;
        itemsSoldVBox.setVisible(false);
        gradeVBox.setVisible(true);
    }
    public void hideGradingShowSales(){
        itemsSoldVBox.setVisible(true);
        gradeVBox.setVisible(false);
    }

    private void checkToWarnGradee(Ratings r){
        if((Ratings.getAverageRating(r.getRatee()) <= 2) && (Ratings.userHasThreeDifferentRaters(r.getRatee())) ){
            Warning w = new Warning();
            w.setOrdinaryUser(currentSession.getUserByUsername(r.getRatee()));
            w.setReason("Your average rating is lower than 2 based on at least three different buyers");
            w.insertWarning();
        }
    }

    private void checkToWarnGrader(Ratings r){
        if(r.isReckless(r.getRater())){
            Warning w = new Warning();
            w.setOrdinaryUser((OrdinaryUser)currentSession.getCurUser());
            w.setReason("You seem to be a reckless grader");
            w.insertWarning();
        }
    }

    private void checkToUpdateVIP(Ratings r){
        if((Ratings.getAverageRating(r.getRatee()) >= 4) && (Ratings.userHasThreeDifferentRaters(r.getRatee()))
                && (!Warning.hasWarnings(r.getRatee()))){
            curController.getAuction().getOrdinaryUser().setVIPStatus(true);
            curController.getAuction().getOrdinaryUser().updateUserInfo();
        }
        else if(curController.getAuction().getOrdinaryUser().isVIPStatus() && (Ratings.getAverageRating(r.getRatee()) < 4)){
            curController.getAuction().getOrdinaryUser().setVIPStatus(false);
            curController.getAuction().getOrdinaryUser().updateUserInfo();

        }
    }

}

