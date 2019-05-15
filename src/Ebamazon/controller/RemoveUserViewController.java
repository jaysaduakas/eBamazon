package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.OrdinaryUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class RemoveUserViewController {

    private CurrentSession currentSession;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button submtButton;

    @FXML
    private ToggleGroup Type;

    @FXML
    private RadioButton radioButton;

    @FXML
    void submit(ActionEvent event) {
        if (!usernameTextField.getText().equals("") && radioButton.isSelected()){
            try{
                OrdinaryUser ou = currentSession.getUserByUsername(usernameTextField.getText());
                ou.setBannedStatus(true);
                ou.updateUserInfo();
                usernameTextField.setText("");
                radioButton.setSelected(false);
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    @FXML
    void initialize() {
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'removeUserlView.fxml'.";
        assert submtButton != null : "fx:id=\"submtButton\" was not injected: check your FXML file 'removeUserlView.fxml'.";
        assert Type != null : "fx:id=\"Type\" was not injected: check your FXML file 'removeUserlView.fxml'.";

    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }
}
