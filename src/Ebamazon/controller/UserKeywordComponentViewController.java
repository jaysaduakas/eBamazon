package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.UserKeyword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class UserKeywordComponentViewController {

    private UserKeyword userKeyword;
    private userSettingsController parent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox keywordView;

    @FXML
    private Label keywordLabel;

    @FXML
    private Button deleteButton;

    @FXML
    void deleteKeyword(ActionEvent event) {
        parent.deleteKeywordItem(this);
    }

    @FXML
    void initialize() {
        assert keywordView != null : "fx:id=\"keywordView\" was not injected: check your FXML file 'UserKeywordComponentView.fxml'.";
        assert keywordLabel != null : "fx:id=\"keywordLabel\" was not injected: check your FXML file 'UserKeywordComponentView.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'UserKeywordComponentView.fxml'.";

    }

    public void setUserKeyword(UserKeyword uk){
        this.userKeyword = uk;
        keywordLabel.setText(uk.getKeyword());
    }

    public UserKeyword getUserKeyword() {
        return userKeyword;
    }

    public HBox getKeywordView() {
        return keywordView;
    }

    public void setParent(userSettingsController parent) {
        this.parent = parent;
    }
}
