package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class TwoWarningViewController {

    private CurrentSession currentSession;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane component;

    @FXML
    private TextArea warngingsText;

    @FXML
    private TextArea responseText;

    @FXML
    private Button submitButton;

    @FXML
    private Button resignButton;

    @FXML
    void resign(ActionEvent event) {
        ((OrdinaryUser)currentSession.getCurUser()).setBannedStatus(true);
        ((OrdinaryUser)currentSession.getCurUser()).changeUserPW(null);
        ((OrdinaryUser)currentSession.getCurUser()).updateUserInfo();
    }

    @FXML
    void submit(ActionEvent event) {
        if (!responseText.getText().equals("")) {
            PendingAppeal pa = new PendingAppeal();
            pa.setUsername(currentSession.getCurUser().getUsername());
            pa.setText(responseText.getText());
            pa.insertAppeal();
        }
    }

    @FXML
    void initialize() {
        assert component != null : "fx:id=\"component\" was not injected: check your FXML file 'Untitled'.";
        assert warngingsText != null : "fx:id=\"warngingsText\" was not injected: check your FXML file 'Untitled'.";
        assert responseText != null : "fx:id=\"responseText\" was not injected: check your FXML file 'Untitled'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'Untitled'.";
        assert resignButton != null : "fx:id=\"resignButton\" was not injected: check your FXML file 'Untitled'.";

    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }

    public void setUpView(){
        //put warnings into text box
        for(Warning w : Warning.getWarningsByUsername(currentSession.getCurUser().getUsername())){
            warngingsText.setText(warngingsText.getText() + w.getReason() + "\n");
        }
    }
}

