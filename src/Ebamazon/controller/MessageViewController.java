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

    //holds info about the current logged in session
    private CurrentSession currentSession;
    //keeps track of the controllers for the individual message views
    private ArrayList<MessageComponentViewController> messageComponentViewControllers;
    //keeps track of the individual view components themselves
    private ArrayList<Node> componentViews;
    //keeps a reference to the currently selected messagecomponetviewcontroller
    private MessageComponentViewController currentMCVC;
    //an index to the current message view utilized by the delete function
    private int curMessageViewIndex;

    //FXML Injection
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

    //called when the new message button is clicked.  Displays the send message pane and zeros the fields out
    @FXML
    void newMessage(ActionEvent event) {
        readMessagePane.setVisible(false);
        sendMessagePane.setVisible(true);
        sendMessageContentTextArea.setText("");
        sendMessageRecipientTextField.setText("");
        sendMessageSubjectTextField.setText("");
    }
    //called by the send button when writing message. creates a new message, sets it using fields, pushes it to db
    @FXML
    void send(ActionEvent event) {
        Message m = new Message();
        m.setSubject(sendMessageSubjectTextField.getText());
        m.setReceiver(sendMessageRecipientTextField.getText());
        m.setSender(currentSession.getCurUser().getUsername());
        m.setMessageContent(sendMessageContentTextArea.getText());
        currentSession.getCurUser().sendMessage(m);
        newMessage(null);
    }

    //called by the delete button.  deletes the selected message view, and deletes the message from the db
    @FXML
    void delete(ActionEvent event) {
        System.out.println(currentMCVC.toString());
        //delete selected message from db
        currentSession.getCurUser().deleteMessage(currentMCVC.getMessage());
        //remove the controller from the list
        messageComponentViewControllers.remove(currentMCVC);
        //remove the message view from the VBox
        messageVBox.getChildren().remove(curMessageViewIndex);
        //hide the message pane
        readMessagePane.setVisible(false);
        //reindex all messages, to maintain accurate representation of every controller in the list
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

    //sets the current session variable, and attaches the messages of associated user
    public void setCurrentSession(CurrentSession currentSession) throws IOException {
        //set curSes
        this.currentSession = currentSession;
        //get all current users messages
        ArrayList<Message> messages = currentSession.getCurUser().getMessages();

        //for each message...
        int index = 0;
        for (Message m : messages){
            //load the messages view
            FXMLLoader messageLoader = new FXMLLoader();
            messageLoader.setLocation(getClass().getResource("../view/messageComponentView.fxml"));
            Node message = messageLoader.load();
            //set the message view controller
            MessageComponentViewController mcvc = messageLoader.getController();
            mcvc.setMvc(this);
            mcvc.setMessage(m);
            mcvc.setMessageIndex(index);
            //keep references to controllers and views, and attach views to the scrollpane
            messageComponentViewControllers.add(mcvc);
            messageVBox.getChildren().add(message);
            componentViews.add(message);
            index++;
        }
    }

    //when a message is clicked the following function is called
    public void messageClicked(MessageComponentViewController mcvc){
        //set the currently clicked mcvc
        currentMCVC = mcvc;
        curMessageViewIndex = mcvc.getMessageIndex();
        //unhighlight every message (the calling function rehighlights the clicked message)
        cycleClicked();
        //get the current message
        Message m = mcvc.getMessage();
        //adjust visible panels
        sendMessagePane.setVisible(false);
        readMessagePane.setVisible(true);
        //set read labels to current message
        readMessageSenderLabel.setText(m.getSender());
        readMessageSubjectLabel.setText(m.getSubject());
        readMessageContentLabel.setText(m.getMessageContent());
    }

    //presets the new message fields with info taken from the message passed as a parammeter
    public void setReply(Message m){
        readMessagePane.setVisible(false);
        sendMessagePane.setVisible(true);
        sendMessageRecipientTextField.setText(m.getSender());
        sendMessageSubjectTextField.setText("RE: " + m.getSubject());
        sendMessageContentTextArea.setText("\n\n------------------------------------------------------------------------------------\n\n" + m.getMessageContent());
    }

    //unhighlight all attached messages
    private void cycleClicked(){
        for (MessageComponentViewController m : messageComponentViewControllers){
            m.unhighlight();
        }
    }
    //a loop that enables the send button when all fields have been filled
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
