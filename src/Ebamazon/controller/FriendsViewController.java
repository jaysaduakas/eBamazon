package Ebamazon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.DataAccessLayer.PendingApplicationDAO;
import Ebamazon.model.Friends;
import Ebamazon.model.Message;
import Ebamazon.model.OrdinaryUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FriendsViewController {

    private CurrentSession currentSession;
    private friendComponentViewController selectedFriend;
    private ArrayList<friendComponentViewController> friendViewControllers;
    private ArrayList<FriendRequestComponentViewController> friendRequestViewControllers;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField findFriendTextField;

    @FXML
    private Button findFriendButton;

    @FXML
    private VBox friendsListVBox;

    @FXML
    private Button sendMessageButton;

    @FXML
    private Button removeFriendButton;

    @FXML
    private VBox friendRequestsVBox;

    @FXML
    private TextField subject;

    @FXML
    private TextArea friendMessageTextArea;

    @FXML
    void removeFriend(ActionEvent event) {
        OrdinaryUser ou = ((OrdinaryUser)currentSession.getCurUser());
        if (ou.deleteFriend(selectedFriend.getFriend())) {
            friendsListVBox.getChildren().remove(selectedFriend.getFriendComponent());
        }
    }

    @FXML
    void sendMessage(ActionEvent event) {
        if (subject.getText().equals("") && friendMessageTextArea.getText().equals("")) return;

        OrdinaryUser ou = (OrdinaryUser)currentSession.getCurUser();
        ArrayList<Message> messages = new ArrayList<>();
        for (Friends f : ou.getFriends()){
            Message message = new Message();
            if (f.getConfirmingFriendID().equals(ou.getUsername())){
                message.setReceiver(f.getSuggestingFriendlD());
                message.setSender(f.getConfirmingFriendID());
            } else {
                message.setReceiver(f.getConfirmingFriendID());
                message.setSender(f.getSuggestingFriendlD());
            }
            message.setSubject("FOM: " + subject.getText());
            message.setMessageContent(friendMessageTextArea.getText() + "\n\n\nSENT AS A FRIENDS ONLY MESSAGE!");
            messages.add(message);
        }
        for (Message m : messages){
            ou.sendMessage(m);
        }
        subject.setText("");
        friendMessageTextArea.setText("");
    }

    @FXML
    void sendRequest(ActionEvent event) {
        OrdinaryUser ou = ((OrdinaryUser)currentSession.getCurUser());
        Friends friend = new Friends();
        friend.setSuggestingFriendlD(ou.getUsername());
        friend.setConfirmingFriendID(findFriendTextField.getText());
        if (ou.makeFriendRequest(friend)){
            //TODO show success
            findFriendTextField.setText("");
        } else {
            //todo show failure
        }
    }

    public void messageClicked(friendComponentViewController fcvc) {
        //set the currently clicked mcvc
        selectedFriend = fcvc;
        //unhighlight every message (the calling function rehighlights the clicked message)
        cycleClicked();
    }

    @FXML
    void initialize() {
        assert findFriendTextField != null : "fx:id=\"findFriendTextField\" was not injected: check your FXML file 'friendsView.fxml'.";
        assert findFriendButton != null : "fx:id=\"findFriendButton\" was not injected: check your FXML file 'friendsView.fxml'.";
        assert friendsListVBox != null : "fx:id=\"friendsListVBox\" was not injected: check your FXML file 'friendsView.fxml'.";
        assert sendMessageButton != null : "fx:id=\"sendMessageButton\" was not injected: check your FXML file 'friendsView.fxml'.";
        assert removeFriendButton != null : "fx:id=\"removeFriendButton\" was not injected: check your FXML file 'friendsView.fxml'.";
        assert friendRequestsVBox != null : "fx:id=\"friendRequestsVBox\" was not injected: check your FXML file 'friendsView.fxml'.";
        friendRequestViewControllers = new ArrayList<>();
        friendViewControllers = new ArrayList<>();
    }

    private void attachFriends() throws IOException {
        OrdinaryUser ou = (OrdinaryUser)currentSession.getCurUser();
        for (Friends f : ou.getFriends()){
            FXMLLoader friendLoader = new FXMLLoader();
            friendLoader.setLocation(getClass().getResource("../view/friendComponentView.fxml"));
            HBox view = friendLoader.load();
            friendComponentViewController fcvc = friendLoader.getController();
            fcvc.setFriend(f, currentSession);
            fcvc.setCurrentSession(currentSession);
            fcvc.setParent(this);
            friendViewControllers.add(fcvc);
            friendsListVBox.getChildren().add(view);
        }
    }

    private void attachFriendRequests() throws IOException {
        OrdinaryUser ou = (OrdinaryUser)currentSession.getCurUser();
        for (Friends f : ou.getFriendRequests()){
            FXMLLoader friendLoader = new FXMLLoader();
            friendLoader.setLocation(getClass().getResource("../view/friendRequestComponentView.fxml"));
            HBox view = friendLoader.load();
            FriendRequestComponentViewController fcvc = friendLoader.getController();
            fcvc.setFriend(f);
            fcvc.setCurrentSession(currentSession);
            friendRequestViewControllers.add(fcvc);
            friendRequestsVBox.getChildren().add(view);
        }
    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }


    public void setCurrentSession(CurrentSession currentSession) throws IOException {
        this.currentSession = currentSession;
        attachFriendRequests();
        attachFriends();
    }

    //unhighlight all attached messages
    private void cycleClicked(){
        for (friendComponentViewController f : friendViewControllers){
            f.unhighlight();
        }
    }
}
