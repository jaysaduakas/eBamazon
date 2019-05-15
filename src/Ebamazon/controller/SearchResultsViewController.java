package Ebamazon.controller;

import Ebamazon.model.AuctionResult;
import Ebamazon.model.CurrentSession;
import Ebamazon.model.SearchParameters;
import Ebamazon.model.UserStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchResultsViewController {
    private CurrentSession currentSession;
    private ArrayList<AuctionComponentViewController> auctions;
    private SearchParameters sp;
    private ToggleGroup sortToggle;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox mainVBox;

    @FXML
    private ScrollPane auctionScrollPane;

    @FXML
    private VBox scrollableVBox;


    @FXML
    private RadioButton ratingsButton;

    @FXML
    private RadioButton relevanceButton;

    @FXML
    void sortByRating(ActionEvent event) {
        if (currentSession.isSortByRelevance()) {
            if (ratingsButton.isSelected()) {
                currentSession.sortSearchResults();
                scrollableVBox.getChildren().clear();
                reloadResults();
                currentSession.setSortByRelevance(false);
            }
        }
    }

    @FXML
    void sortByRelevance(ActionEvent event) {
        if (!currentSession.isSortByRelevance()) {
            if (relevanceButton.isSelected()) {
                currentSession.sortSearchResults();
                scrollableVBox.getChildren().clear();
                reloadResults();
                currentSession.setSortByRelevance(true);
            }
        }
    }

    @FXML
    void initialize()  {
        auctionScrollPane.setFitToHeight(true);
        auctionScrollPane.setFitToWidth(true);
        auctions = new ArrayList<>();
        assert ratingsButton != null : "fx:id=\"ratingsButton\" was not injected: check your FXML file 'searchResultsView.fxml'.";
        assert relevanceButton != null : "fx:id=\"relevanceButton\" was not injected: check your FXML file 'searchResultsView.fxml'.";

        sortToggle = new ToggleGroup();
        ratingsButton.setToggleGroup(sortToggle);
        relevanceButton.setToggleGroup(sortToggle);
        relevanceButton.setSelected(true);
    }

    private void attachNewAuction(AuctionResult auctionResult){
        FXMLLoader auctionComponentLoader = new FXMLLoader();
        auctionComponentLoader.setLocation(getClass().getResource("../view/auctionComponentView.fxml"));
        try {
            Node auctionComponent = auctionComponentLoader.load();
            scrollableVBox.getChildren().add(auctionComponent);
        }
        catch (Exception e){
            System.out.println("auction component controller not loaded");
        }
        AuctionComponentViewController acvc = auctionComponentLoader.getController();
        acvc.setCurrentSession(currentSession);
        acvc.setAuctionResult(auctionResult);
        acvc.setUpAuction(auctionResult, currentSession);



        if (currentSession.getCurUser().getUserStatus()!=UserStatus.OU) {
            acvc.getBidButton().setVisible(false);
            acvc.getBidBox().setVisible(false);
        }
        auctions.add(acvc);
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;

    }

    public void setSp(SearchParameters sp) throws SQLException {
        this.sp = sp;
        for (AuctionResult a : currentSession.generateSearchResults(sp)){
            if (a.isApprovalStatus() && a.isLiveStatus()){
                attachNewAuction(a);
            }
        }
    }

    private void reloadResults() {
        for (AuctionResult ar : currentSession.getCurrentSearchResults()) {
            attachNewAuction(ar);
        }
    }
}
