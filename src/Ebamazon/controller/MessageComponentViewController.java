package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MessageComponentViewController {

    private Message message;
    private MessageViewController mvc;
    private int messageIndex;

    @FXML
    private HBox messageBox;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label senderLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Button replyButton;

    @FXML
    void clickDetected(MouseEvent event) {
        mvc.messageClicked(this);
        highLight();
    }

    @FXML
    void reply(ActionEvent event) {
        mvc.setReply(message);
    }

    @FXML
    void initialize() {
        assert senderLabel != null : "fx:id=\"senderLabel\" was not injected: check your FXML file 'messageComponentView.fxml'.";
        assert dateLabel != null : "fx:id=\"dateLabel\" was not injected: check your FXML file 'messageComponentView.fxml'.";
        assert subjectLabel != null : "fx:id=\"subjectLabel\" was not injected: check your FXML file 'messageComponentView.fxml'.";
        assert replyButton != null : "fx:id=\"replyButton\" was not injected: check your FXML file 'messageComponentView.fxml'.";
        unhighlight();
    }

    public void setMessage(Message m){
        this.message = m;
        senderLabel.setText(m.getSender());
        dateLabel.setText(m.getDateTimeSent().toString());
        subjectLabel.setText(m.getSubject());
    }

    public void setMvc(MessageViewController mvc){
        this.mvc = mvc;
    }
    public void setMessageIndex(int i) {this.messageIndex = i;}

    public Message getMessage() {
        return message;
    }
    public int getMessageIndex(){return messageIndex;}

    private void highLight(){
        messageBox.setBackground(new Background(new BackgroundFill(Color.rgb(109, 165, 255), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void unhighlight(){
        messageBox.setBackground(new Background(new BackgroundFill(Color.rgb(209,209,209), CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
