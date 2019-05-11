package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ProcessApplicationViewController {

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
    private TextField stateField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField creditcardField;

    @FXML
    private RadioButton acceptRadioButton;

    @FXML
    private ToggleGroup Type;

    @FXML
    private RadioButton denyRadioButton;

    @FXML
    private Button submitButton;

    @FXML
    private Label numberRemainingLabel;

    @FXML
    void submitNewUser(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert stateField != null : "fx:id=\"stateField\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert phoneNumberField != null : "fx:id=\"phoneNumberField\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert creditcardField != null : "fx:id=\"creditcardField\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert acceptRadioButton != null : "fx:id=\"acceptRadioButton\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert Type != null : "fx:id=\"Type\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert denyRadioButton != null : "fx:id=\"denyRadioButton\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'processApplicationView.fxml'.";
        assert numberRemainingLabel != null : "fx:id=\"numberRemainingLabel\" was not injected: check your FXML file 'processApplicationView.fxml'.";

    }
}
