package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class RemoveUserViewController {

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
    void submit(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'removeUserlView.fxml'.";
        assert submtButton != null : "fx:id=\"submtButton\" was not injected: check your FXML file 'removeUserlView.fxml'.";
        assert Type != null : "fx:id=\"Type\" was not injected: check your FXML file 'removeUserlView.fxml'.";

    }
}
