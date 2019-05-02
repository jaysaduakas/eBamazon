package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.OrdinaryUser;
import Ebamazon.model.UserStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class userSettingsController {

    private BorderPane parent;
    private CurrentSession currentSession;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField creditcardField;

    @FXML
    private Button submitButton;

    @FXML
    void submitUserSettings(ActionEvent event) {
        if (currentSession.getUserStatus() == UserStatus.OU) {
            OrdinaryUser ou = (OrdinaryUser) currentSession.getCurUser();
            if (!nameField.getText().equals("")) {
                ou.setName(nameField.getText());
            }
            if (!addressField.getText().equals("")) {
                ou.setAddress(addressField.getText());
            }
            if (!phoneNumberField.getText().equals("")) {
                ou.setPhone(phoneNumberField.getText());
            }
            if (!creditcardField.getText().equals("")) {
                ou.setCc(creditcardField.getText());
            }
            if (ou.updateUserInfo()) {
                System.out.println("User info updated in database");
                currentSession.setCurUser(ou);
            }

            if (!passwordField.getText().equals("")) {
                if (ou.changeUserPW(passwordField.getText())){
                    System.out.println("Password updated in database");
                }
            }
        }
    }

    @FXML
    void initialize() {
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert phoneNumberField != null : "fx:id=\"phoneNumberField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert creditcardField != null : "fx:id=\"creditcardField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'userSettingsView.fxml'.";

    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }
}
