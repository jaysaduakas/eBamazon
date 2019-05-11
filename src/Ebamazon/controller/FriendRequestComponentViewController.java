package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.Friends;
import Ebamazon.model.OrdinaryUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class FriendRequestComponentViewController {

    private Friends friend;
    private CurrentSession currentSession;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox friendComponent;

    @FXML
    private Label username;

    @FXML
    private Button confirmButton;

    @FXML
    void confirm(ActionEvent event) {
        ((OrdinaryUser)currentSession.getCurUser()).confirmFriend(friend);
        confirmButton.setDisable(true);
        confirmButton.setText("Confirmed!!!");
    }

    @FXML
    void initialize() {
        assert friendComponent != null : "fx:id=\"friendComponent\" was not injected: check your FXML file 'friendRequestComponentView.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'friendRequestComponentView.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'friendRequestComponentView.fxml'.";

    }

    public Friends getFriend() {
        return friend;
    }

    public void setFriend(Friends friend) {
        this.friend = friend;
        username.setText(friend.getSuggestingFriendlD());
    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }
}
