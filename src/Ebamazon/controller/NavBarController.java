package Ebamazon.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.*;
import Ebamazon.model.DataAccessLayer.AuctionImageDAO;
import javafx.animation.AnimationTimer;
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
    private Button MessagingButton;

    @FXML
    private Button FriendsButton;

    @FXML
    private Button lodgeComplaintButton;

    @FXML
    private Button userSettingsButton;

    @FXML
    private VBox viewComponent;

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
    void loadMessagingView(ActionEvent event) throws IOException {
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
    void loadFriendsView(ActionEvent event) throws IOException {
        FXMLLoader friendsViewLoader = new FXMLLoader();
        friendsViewLoader.setLocation(getClass().getResource("../view/friendsView.fxml"));
        AnchorPane view = friendsViewLoader.load();

        FriendsViewController fvc = friendsViewLoader.getController();
        fvc.setCurrentSession(currentSession);

        parent.setCenter(view);
    }

    @FXML
    void loadHomeView(ActionEvent event) throws IOException, SQLException {
        FXMLLoader searchResultsLoader = new FXMLLoader();
        searchResultsLoader.setLocation(getClass().getResource("../view/searchResultsView.fxml"));
        AnchorPane view = searchResultsLoader.load();
        SearchResultsViewController srvc = searchResultsLoader.getController();
        srvc.setCurrentSession(currentSession);
        if (currentSession.getUserStatus()==UserStatus.OU) {
            SearchParameters sp = generateSearchParametersFromUser();
            try {
                srvc.setSp(sp);
                srvc.getBannerLabel().setText("Auctions For You!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        parent.setCenter(view);
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
    void loadMyAuctionView(ActionEvent event) throws IOException {
        FXMLLoader myAuctionViewLoader = new FXMLLoader();
        myAuctionViewLoader.setLocation(getClass().getResource("../view/myAuctionsView.fxml"));
        AnchorPane view = myAuctionViewLoader.load();
        MyAuctionsViewController mavc = myAuctionViewLoader.getController();
        parent.setCenter(view);
        mavc.setCurrentSession(currentSession);
        mavc.setParent(parent);
        mavc.populateAuctionList((OrdinaryUser)currentSession.getCurUser());
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
    void loadTransactionHistoryView(ActionEvent event) throws IOException {
        //intialize settingsViewLoader
        FXMLLoader transLoader = new FXMLLoader();
        transLoader.setLocation(getClass().getResource("../view/transactionHistoryView.fxml"));
        AnchorPane trans = transLoader.load();
        //set private members for controller
        TransactionHistoryViewController thvc = transLoader.getController();
        thvc.setCurrentSession(currentSession);
        //set center display to view
        parent.setCenter(trans);
    }

    @FXML
    void initialize() {
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert myAuctionsButton != null : "fx:id=\"myAuctionsButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert createAuctionButton != null : "fx:id=\"createAuctionButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert transactionHistoryButton != null : "fx:id=\"transactionHistoryButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert MessagingButton != null : "fx:id=\"friendsAndMessagingButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert lodgeComplaintButton != null : "fx:id=\"lodgeComplaintButton\" was not injected: check your FXML file 'navBarView.fxml'.";
        assert userSettingsButton != null : "fx:id=\"userSettingsButton\" was not injected: check your FXML file 'navBarView.fxml'.";

    }

    public void configureButtons(CurrentSession cs){
        if (cs.getUserStatus() == UserStatus.OU){
            myAuctionsButton.setVisible(true);
            createAuctionButton.setVisible(true);
            transactionHistoryButton.setVisible(true);
            MessagingButton.setVisible(true);
            FriendsButton.setVisible(true);
            lodgeComplaintButton.setVisible(true);
            userSettingsButton.setVisible(true);
        }
        else if (cs.getUserStatus() == UserStatus.GU){
            myAuctionsButton.setVisible(false);
            createAuctionButton.setVisible(false);
            transactionHistoryButton.setVisible(false);
            MessagingButton.setVisible(false);
            FriendsButton.setVisible(false);
            lodgeComplaintButton.setVisible(false);
            userSettingsButton.setVisible(false);
        }
    }

    private SearchParameters generateSearchParametersFromUser() { //Marissa added this!
        OrdinaryUser ou = (OrdinaryUser) currentSession.getCurUser();
        ArrayList<UserKeyword> ouKeywords = new ArrayList<>();
        ouKeywords = ou.getKeywords();
        String keywords = "";
        for (UserKeyword uk : ouKeywords){
            keywords += uk.getKeyword() + " ";
        }
        SearchParameters sp = new SearchParameters(keywords);
        sp.setShowAuction(true);
        sp.setShowFixed(true);
        return sp;
    }


    //getters and setters

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }

    public VBox getViewComponent() {
        return viewComponent;
    }
}
