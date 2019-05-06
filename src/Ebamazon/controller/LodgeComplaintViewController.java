package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.Complaint;
import Ebamazon.model.CurrentSession;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class LodgeComplaintViewController {

    private CurrentSession currentSession;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label userNotFoundLabel;

    @FXML
    private TextArea complaintDescTextArea;

    @FXML
    private Pane successPane;

    @FXML
    private Button submtButton;

    @FXML
    void submit(ActionEvent event) {
        Complaint c = new Complaint();
        c.setSender(currentSession.getCurUser().getUsername());
        c.setComplainee(usernameTextField.getText());
        c.setComplaint(complaintDescTextArea.getText());
        if (currentSession.getCurUser().verifyUserExists(c.getComplainee())){
            currentSession.getCurUser().lodgeComplaint(c);
            successPane.setVisible(true);
            userNotFoundLabel.setVisible(false);
            usernameTextField.setText("");
            complaintDescTextArea.setText("");
        }
        else {
            successPane.setVisible(false);
            userNotFoundLabel.setVisible(true);
        }
    }

    @FXML
    void initialize() {
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'lodgeComplaintView.fxml'.";
        assert userNotFoundLabel != null : "fx:id=\"userNotFoundLabel\" was not injected: check your FXML file 'lodgeComplaintView.fxml'.";
        assert complaintDescTextArea != null : "fx:id=\"complaintDescTextArea\" was not injected: check your FXML file 'lodgeComplaintView.fxml'.";
        assert successPane != null : "fx:id=\"successPane\" was not injected: check your FXML file 'lodgeComplaintView.fxml'.";
        assert submtButton != null : "fx:id=\"submtButton\" was not injected: check your FXML file 'lodgeComplaintView.fxml'.";
        validateSubmit();
    }

    private void validateSubmit(){
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!usernameTextField.getText().equals("") && !complaintDescTextArea.getText().equals("")){
                    submtButton.setDisable(false);
                }
                else {
                    submtButton.setDisable(true);
                }
            }
        };
        at.start();
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }
}
