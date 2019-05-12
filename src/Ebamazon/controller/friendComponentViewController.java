package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.Friends;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class friendComponentViewController {

    private Friends friend;
    private CurrentSession currentSession;
    private FriendsViewController parent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox friendComponent;

    @FXML
    private Label username;

    @FXML
    private Label realname;


    @FXML
    void clickDetected(MouseEvent event) {
        parent.messageClicked(this);
        highLight();
    }

    @FXML
    void initialize() {
        assert friendComponent != null : "fx:id=\"friendComponent\" was not injected: check your FXML file 'friendComponentView.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'friendComponentView.fxml'.";
        assert realname != null : "fx:id=\"realname\" was not injected: check your FXML file 'friendComponentView.fxml'.";

    }

    public void setFriend(Friends friend, CurrentSession cs) {

        this.friend = friend;
        String friendName;
        if ((friend.getConfirmingFriendID().toLowerCase()).equals(cs.getCurUser().getUsername().toLowerCase())) {
            username.setText(friend.getSuggestingFriendlD());
            friendName = (cs.getUserByUsername(friend.getSuggestingFriendlD())).getName();
        }
        else{
            username.setText(friend.getConfirmingFriendID());
            friendName = (cs.getUserByUsername(friend.getConfirmingFriendID())).getName();
        }
        realname.setText(friendName);
    }


    public HBox getFriendComponent() {
        return friendComponent;
    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }

    //change highlighted message color
    private void highLight(){
        friendComponent.setBackground(new Background(new BackgroundFill(Color.rgb(109, 165, 255), CornerRadii.EMPTY, Insets.EMPTY)));
    }
    //change message to unhighlighted color
    public void unhighlight(){
        friendComponent.setBackground(new Background(new BackgroundFill(Color.rgb(209,209,209), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public Friends getFriend() {
        return friend;
    }

    public FriendsViewController getParent() {
        return parent;
    }

    public void setParent(FriendsViewController parent) {
        this.parent = parent;
    }
}
