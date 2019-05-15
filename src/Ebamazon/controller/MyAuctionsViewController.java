package Ebamazon.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Ebamazon.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MyAuctionsViewController {

    private CurrentSession currentSession;
    private BorderPane parent;


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
    private AnchorPane componentView;

    @FXML
    void initialize() {
        assert mainVBox != null : "fx:id=\"mainVBox\" was not injected: check your FXML file 'myAuctionsView.fxml'.";
        assert auctionScrollPane != null : "fx:id=\"auctionScrollPane\" was not injected: check your FXML file 'myAuctionsView.fxml'.";
        assert scrollableVBox != null : "fx:id=\"scrollableVBox\" was not injected: check your FXML file 'myAuctionsView.fxml'.";

    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }

    public void populateAuctionList(OrdinaryUser ou){
        try {
            for (Auction a : ou.getMyAuctions()){
                if (!a.isDenied() && !a.isKickback()) {
                    FXMLLoader auctionComponentViewLoader = new FXMLLoader();
                    auctionComponentViewLoader.setLocation(getClass().getResource("../view/auctionComponentView.fxml"));
                    AnchorPane view = auctionComponentViewLoader.load();
                    AuctionComponentViewController acvc = auctionComponentViewLoader.getController();
                    acvc.setAuction(a);
                    acvc.getTitle().setText(a.getTitle());
                    acvc.getBidBox().setVisible(false);
                    acvc.getBidButton().setVisible(false);
                    acvc.getCreator().setText(a.getOrdinaryUser().getUsername());
                    acvc.getDesc().setText(a.getDescription());
                    if (a.getDateTimeConfirmed() != null) {
                        acvc.getDate().setText(a.getDateTimeConfirmed().toString());
                    } else {
                        acvc.getDate().setText("Auction Is Not Yet Confirmed by SuperUser");
                    }
                    acvc.getPrice().setText("Price: " + a.getPrice());
                    Image image = null;
                    for (AuctionImage i : a.getAuctionImages()) {
                        if (i.isDefaultPhoto()) {
                            image = i.getImage();
                        }
                    }
                    String keywords = "";
                    for (AuctionKeyword k : a.getKeywords()) {
                        keywords += k.getKeyword() + ", ";
                    }
                    keywords = keywords.substring(0, keywords.length() - 2);
                    acvc.getKeywords().setText(keywords);
                    acvc.updateDisplayImage(image);
                    scrollableVBox.getChildren().add(view);
                    if(!a.isLiveStatus()){
                        acvc.getLiveCircle().setVisible(false);
                    }else {
                        acvc.getViewBidsButton().setVisible(true);
                        acvc.setCurrentSession(currentSession);
                        acvc.setParent(parent);
                    }
                    if(a.isFixed()){
                        acvc.getFixedLabel().setVisible(true);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }
}
