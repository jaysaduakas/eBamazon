package Ebamazon.controller;

import Ebamazon.model.AuctionResult;
import Ebamazon.model.CurrentSession;
import Ebamazon.model.SearchParameters;
import Ebamazon.model.UserStatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
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
    void initialize()  {
        auctionScrollPane.setFitToHeight(true);
        auctionScrollPane.setFitToWidth(true);
        auctions = new ArrayList<>();

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
        acvc.setUpAuction(auctionResult, currentSession);
        acvc.setCurrentSession(currentSession);
        acvc.setAuctionResult(auctionResult);

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
}
