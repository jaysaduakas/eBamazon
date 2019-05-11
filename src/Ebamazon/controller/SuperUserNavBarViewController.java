package Ebamazon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SuperUserNavBarViewController {

    private CurrentSession currentSession;
    private BorderPane parent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button homeButton;

    @FXML
    private Button processApplicationsButton;

    @FXML
    private Button processAuctionsButton;

    @FXML
    private Button processComplaintsButton;

    @FXML
    private Button processAppealsButton;

    @FXML
    private Button issueWarningButton;

    @FXML
    private Button removeUserButton;

    @FXML
    private Button tabooWordListButton;

    @FXML
    private Button transactionStatsButton;

    @FXML
    void loadApplicationView(ActionEvent event) {

    }

    @FXML
    void loadAuctionView(ActionEvent event) {

    }

    @FXML
    void loadComplaintView(ActionEvent event) {

    }

    @FXML
    void loadHomeView(ActionEvent event) {

    }

    @FXML
    void loadRemoveView(ActionEvent event) {

    }

    @FXML
    void loadTabooView(ActionEvent event) throws IOException {
        FXMLLoader tabooViewLoader = new FXMLLoader();
        tabooViewLoader.setLocation(getClass().getResource("../view/maintainTabooView.fxml"));
        AnchorPane view = tabooViewLoader.load();
        MaintainTabooViewController mtvc = tabooViewLoader.getController();
        mtvc.setCurrentSession(currentSession);
        parent.setCenter(view);
    }

    @FXML
    void loadTransactionView(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'superUserNavBarView.fxml'.";
        assert processApplicationsButton != null : "fx:id=\"processApplicationsButton\" was not injected: check your FXML file 'superUserNavBarView.fxml'.";
        assert processAuctionsButton != null : "fx:id=\"processAuctionsButton\" was not injected: check your FXML file 'superUserNavBarView.fxml'.";
        assert processComplaintsButton != null : "fx:id=\"processComplaintsButton\" was not injected: check your FXML file 'superUserNavBarView.fxml'.";
        assert processAppealsButton != null : "fx:id=\"processAppealsButton\" was not injected: check your FXML file 'superUserNavBarView.fxml'.";
        assert issueWarningButton != null : "fx:id=\"issueWarningButton\" was not injected: check your FXML file 'superUserNavBarView.fxml'.";
        assert removeUserButton != null : "fx:id=\"removeUserButton\" was not injected: check your FXML file 'superUserNavBarView.fxml'.";
        assert tabooWordListButton != null : "fx:id=\"tabooWordListButton\" was not injected: check your FXML file 'superUserNavBarView.fxml'.";
        assert transactionStatsButton != null : "fx:id=\"transactionStatsButton\" was not injected: check your FXML file 'superUserNavBarView.fxml'.";

    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }
}
