package Ebamazon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.AuctionImage;
import Ebamazon.model.CurrentSession;
import Ebamazon.model.DataAccessLayer.AuctionImageDAO;
import Ebamazon.model.UserStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NavBarController {

    private BorderPane parent;
    private CurrentSession currentSession;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button homeButton;

    @FXML
    private Button myAuctionsButton;

    @FXML
    private Button createAuctionButton;

    @FXML
    private Button transactionHistoryButton;

    @FXML
    private Button friendsAndMessagingButton;

    @FXML
    private Button lodgeComplaintButton;

    @FXML
    private Button userSettingsButton;

    @FXML
    void loadCreateAuctionView(ActionEvent event) throws IOException {
        FXMLLoader createAuctionLoader = new FXMLLoader();
        createAuctionLoader.setLocation(getClass().getResource("../view/createAuctionView.fxml"));
        AnchorPane createAuctionView = createAuctionLoader.load();

        CreateAuctionViewController cavc = createAuctionLoader.getController();
        cavc.setCurrentSession(currentSession);

        parent.setCenter(createAuctionView);
    }

    @FXML
    void loadFriendsAndMessagingView(ActionEvent event) throws IOException {
        //intialize settingsViewLoader
        FXMLLoader messageLoader = new FXMLLoader();
        messageLoader.setLocation(getClass().getResource("../view/messageView.fxml"));
        AnchorPane messageView = messageLoader.load();
        //set private members for controller
        MessageViewController mvc = messageLoader.getController();
        mvc.setCurrentSession(currentSession);
        //set center display to view
        parent.setCenter(messageView);
    }

    @FXML
    void loadHomeView(ActionEvent event) {
        VBox vBox = new VBox();
        for (AuctionImage a : AuctionImageDAO.getAuctionImages(1)) {
            vBox.getChildren().add(new ImageView(new Image(a.getImageFile().toURI().toString())));
        }
        parent.setCenter(vBox);
    }

    @FXML
    void loadLodgeComplaintView(ActionEvent event) throws IOException {
        FXMLLoader complaintViewLoader = new FXMLLoader();
        complaintViewLoader.setLocation(getClass().getResource("../view/lodgeComplaintView.fxml"));
        AnchorPane complaintView = complaintViewLoader.load();

        LodgeComplaintViewController lcvc = complaintViewLoader.getController();
        lcvc.setCurrentSession(currentSession);

        parent.setCenter(complaintView);
    }

    @FXML
    void loadMyAuctionView(ActionEvent event) {

    }

    @FXML
    void loadSettingsView(ActionEvent event) throws IOException {
        //intialize settingsViewLoader
        FXMLLoader settingsLoader = new FXMLLoader();
        settingsLoader.setLocation(getClass().getResource("../view/userSettingsView.fxml"));
        AnchorPane settings = settingsLoader.load();
        //set private members for controller
        userSettingsController usc = settingsLoader.getController();
        usc.setParent(parent);
        usc.setCurrentSession(currentSession);
        //set center display to view
        parent.setCenter(settings);
    }

    @FXML
    void loadTransactionHistoryView(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert myAuctionsButton != null : "fx:id=\"myAuctionsButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert createAuctionButton != null : "fx:id=\"createAuctionButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert transactionHistoryButton != null : "fx:id=\"transactionHistoryButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert friendsAndMessagingButton != null : "fx:id=\"friendsAndMessagingButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert lodgeComplaintButton != null : "fx:id=\"lodgeComplaintButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert userSettingsButton != null : "fx:id=\"userSettingsButton\" was not injected: check your FXML file 'navBarView.fxml'.";

    }

    public void configureButtons(CurrentSession cs){
        if (cs.getUserStatus() == UserStatus.OU){
            myAuctionsButton.setVisible(true);
            createAuctionButton.setVisible(true);
            transactionHistoryButton.setVisible(true);
            friendsAndMessagingButton.setVisible(true);
            lodgeComplaintButton.setVisible(true);
            userSettingsButton.setVisible(true);
        }
        else if (cs.getUserStatus() == UserStatus.GU){
            myAuctionsButton.setVisible(false);
            createAuctionButton.setVisible(false);
            transactionHistoryButton.setVisible(false);
            friendsAndMessagingButton.setVisible(false);
            lodgeComplaintButton.setVisible(false);
            userSettingsButton.setVisible(false);
        }
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }
}
