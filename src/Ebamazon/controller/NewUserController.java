package Ebamazon.controller;
import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.OrdinaryUser;
import Ebamazon.model.State;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NewUserController {

    private AnimationTimer animationTimer;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private ComboBox<State> stateField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField creditcardField;

    @FXML
    private Button submitButton;

    @FXML
    private Label usernameTaken;

    @FXML
    private Label applicationSubmitted;

    @FXML
    void submitNewUser(ActionEvent event) {
        OrdinaryUser ou = new OrdinaryUser();
        ou.setUsername(usernameField.getText());
        ou.setAddress(addressField.getText());
        ou.setCc(creditcardField.getText());
        ou.setPhone(phoneNumberField.getText());
        ou.setName(nameField.getText());
        ou.setStateID(stateField.getValue().getAbbreviation());
        if (ou.insertUserInfo()){
            applicationSubmitted.setVisible(true);
            usernameTaken.setVisible(false);
        }
        else {
            applicationSubmitted.setVisible(false);
            usernameTaken.setVisible(true);
        }
        //FAILING BECAUSE IT NEEDS A STATE ID!!!!!
    }

    @FXML
    void initialize() {
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'newUserView.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'newUserView.fxml'.";
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'newUserView.fxml'.";
        assert phoneNumberField != null : "fx:id=\"phoneNumberField\" was not injected: check your FXML file 'newUserView.fxml'.";
        assert creditcardField != null : "fx:id=\"creditcardField\" was not injected: check your FXML file 'newUserView.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'newUserView.fxml'.";
        assert stateField != null : "fx:id=\"stateField\" was not injected: check your FXML file 'newUserView.fxml'.";
        stateField.getItems().setAll(State.values());
        updateLoop();
    }

    private void updateLoop(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!usernameField.getText().equals("") && !nameField.getText().equals("") && !addressField.getText().equals("") &&
                        !phoneNumberField.getText().equals("") && !creditcardField.getText().equals("") && stateField.getValue()!=null){
                    submitButton.setDisable(false);
                }
            }
        };
        animationTimer.start();
    }

}
