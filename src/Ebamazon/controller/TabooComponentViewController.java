package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.Taboo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TabooComponentViewController {

    private String taboo;
    private CurrentSession currentSession;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox tabooComponent;

    @FXML
    private Label tabooLabel;

    @FXML
    private Button removeButton;

    @FXML
    void remove(ActionEvent event) {
        removeButton.setDisable(true);
        removeButton.setText("REMOVED");
        currentSession.deleteTaboo(taboo);
    }

    @FXML
    void initialize() {
        assert tabooComponent != null : "fx:id=\"tabooComponent\" was not injected: check your FXML file 'tabooComponentView.fxml'.";
        assert tabooLabel != null : "fx:id=\"tabooLabel\" was not injected: check your FXML file 'tabooComponentView.fxml'.";
        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'tabooComponentView.fxml'.";

    }

    public void setTaboo(String taboo){
        this.taboo = taboo;
        tabooLabel.setText(taboo);
    }

    public void setCurrentSession(CurrentSession currentSession){
        this.currentSession = currentSession;
    }
}
