package Ebamazon.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.UserStatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class HomeViewController {

    private CurrentSession currentSession;
    private ArrayList<AuctionComponentViewController> auctions;

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
    void initialize() throws IOException {
        auctionScrollPane.setFitToHeight(true);
        auctionScrollPane.setFitToWidth(true);
        auctions = new ArrayList<>();
        attachNewAuction();
        attachNewAuction();
        attachNewAuction();
        attachNewAuction();
    }

    private void attachNewAuction(){
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
        auctions.add(acvc);
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
        //if ordinary user
        if (currentSession.getUserStatus()== UserStatus.OU){
            //change image file test code
            auctions.get(0).updateDisplayImage(new Image(getClass().getResourceAsStream("../../resources/testimage2.png")));
        }
        //if guest user
        else if (currentSession.getUserStatus() == UserStatus.GU){
            auctions.get(0).updateDisplayImage(new Image(getClass().getResourceAsStream("../../resources/testimage.png")));
        }
    }
}
