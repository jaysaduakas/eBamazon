package Ebamazon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.Message;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MessageViewController {

    private CurrentSession currentSession;
    private ArrayList<MessageComponentViewController> messageComponentViewControllers;
    private ArrayList<Node> componentViews;
    private MessageComponentViewController currentMCVC;
    private int curMessageViewIndex;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteButton;

    @FXML
    private ScrollPane messageScrollPane;

    @FXML
    private VBox messageVBox;

    @FXML
    private Pane readMessagePane;

    @FXML
    private Label readMessageContentLabel;

    @FXML
    private Label readMessageSubjectLabel;

    @FXML
    private Label readMessageSenderLabel;

    @FXML
    private Button newMessageButton;

    @FXML
    private Pane sendMessagePane;

    @FXML
    private TextField sendMessageSubjectTextField;

    @FXML
    private TextArea sendMessageContentTextArea;

    @FXML
    private TextField sendMessageRecipientTextField;

    @FXML
    private Button sendButton;

    @FXML
    void newMessage(ActionEvent event) {
        readMessagePane.setVisible(false);
        sendMessagePane.setVisible(true);
        sendMessageContentTextArea.setText("");
        sendMessageRecipientTextField.setText("");
        sendMessageSubjectTextField.setText("");
    }

    @FXML
    void send(ActionEvent event) {
        Message m = new Message();
        m.setSubject(sendMessageSubjectTextField.getText());
        m.setReceiver(sendMessageRecipientTextField.getText());
        m.setSender(currentSession.getCurUser().getUsername());
        m.setMessageContent(sendMessageContentTextArea.getText());
        currentSession.getCurUser().sendMessage(m);
    }

    @FXML
    void delete(ActionEvent event) {
        currentSession.getCurUser().deleteMessage(currentMCVC.getMessage());
        messageComponentViewControllers.remove(currentMCVC);
        messageVBox.getChildren().remove(curMessageViewIndex);
        readMessagePane.setVisible(false);
        //reindex all messages
        int index = 0;
        for (MessageComponentViewController mcvc : messageComponentViewControllers){
            mcvc.setMessageIndex(index);
            index++;
        }
    }

    @FXML
    void initialize() {
        assert messageScrollPane != null : "fx:id=\"messageScrollPane\" was not injected: check your FXML file 'messageView.fxml'.";
        assert messageVBox != null : "fx:id=\"messageVBox\" was not injected: check your FXML file 'messageView.fxml'.";
        assert readMessagePane != null : "fx:id=\"readMessagePane\" was not injected: check your FXML file 'messageView.fxml'.";
        assert readMessageContentLabel != null : "fx:id=\"readMessageContentLabel\" was not injected: check your FXML file 'messageView.fxml'.";
        assert readMessageSubjectLabel != null : "fx:id=\"readMessageSubjectLabel\" was not injected: check your FXML file 'messageView.fxml'.";
        assert readMessageSenderLabel != null : "fx:id=\"readMessageSenderLabel\" was not injected: check your FXML file 'messageView.fxml'.";
        assert newMessageButton != null : "fx:id=\"newMessageButton\" was not injected: check your FXML file 'messageView.fxml'.";
        assert sendMessagePane != null : "fx:id=\"sendMessagePane\" was not injected: check your FXML file 'messageView.fxml'.";
        assert sendMessageSubjectTextField != null : "fx:id=\"sendMessageSubjectTextField\" was not injected: check your FXML file 'messageView.fxml'.";
        assert sendMessageContentTextArea != null : "fx:id=\"sendMessageContentTextArea\" was not injected: check your FXML file 'messageView.fxml'.";
        assert sendMessageRecipientTextField != null : "fx:id=\"sendMessageRecipientTextField\" was not injected: check your FXML file 'messageView.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'messageView.fxml'.";
        assert deleteButton != null : "delete button is missing";
        sendButton.setDisable(true);
        messageComponentViewControllers = new ArrayList<>();
        componentViews = new ArrayList<>();
        validateSendLoop();
    }

    public void setCurrentSession(CurrentSession currentSession) throws IOException {
        this.currentSession = currentSession;
        ArrayList<Message> messages = currentSession.getCurUser().getMessages();
        int index = 0;
        for (Message m : messages){
            FXMLLoader messageLoader = new FXMLLoader();
            messageLoader.setLocation(getClass().getResource("../view/messageComponentView.fxml"));
            Node message = messageLoader.load();
            MessageComponentViewController mcvc = messageLoader.getController();
            mcvc.setMvc(this);
            mcvc.setMessage(m);
            mcvc.setMessageIndex(index);
            messageComponentViewControllers.add(mcvc);
            messageVBox.getChildren().add(message);
            componentViews.add(message);
            index++;
        }
    }

    public void messageClicked(MessageComponentViewController mcvc){
        currentMCVC = mcvc;
        curMessageViewIndex = mcvc.getMessageIndex();
        cycleClicked();
        Message m = mcvc.getMessage();
        sendMessagePane.setVisible(false);
        readMessagePane.setVisible(true);
        readMessageSenderLabel.setText(m.getSender());
        readMessageSubjectLabel.setText(m.getSubject());
        readMessageContentLabel.setText(m.getMessageContent());
    }

    public void setReply(Message m){
        readMessagePane.setVisible(false);
        sendMessagePane.setVisible(true);
        sendMessageRecipientTextField.setText(m.getSender());
        sendMessageSubjectTextField.setText("RE: " + m.getSubject());
        sendMessageContentTextArea.setText("\n\n------------------------------------------------------------------------------------\n\n" + m.getMessageContent());
    }

    private void cycleClicked(){
        for (MessageComponentViewController m : messageComponentViewControllers){
            m.unhighlight();
        }
    }

    private void validateSendLoop(){
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!sendMessageContentTextArea.getText().equals("") && !sendMessageRecipientTextField.getText().equals("") && !sendMessageSubjectTextField.getText().equals("")){
                    sendButton.setDisable(false);
                }
                else
                {
                    sendButton.setDisable(true);
                }
            }
        };
        at.start();
    }
}
