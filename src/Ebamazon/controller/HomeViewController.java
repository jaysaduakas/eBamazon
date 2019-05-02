package Ebamazon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.UserStatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class HomeViewController {

    private CurrentSession currentSession;
    private ArrayList<Node> auctions;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox mainVBox;

    @FXML
    void initialize() throws IOException {
        FXMLLoader auctionComponentLoader = new FXMLLoader();
        auctionComponentLoader.setLocation(getClass().getResource("../view/auctionComponentView.fxml"));
        Node auctionComponent = auctionComponentLoader.load();
        auctions = new ArrayList<>();
        auctions.add(auctionComponent);
        mainVBox.getChildren().add(auctionComponent);
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
        if (currentSession.getUserStatus()== UserStatus.OU){
            //change image file
            //auctions.get(0).
        }
    }
}
