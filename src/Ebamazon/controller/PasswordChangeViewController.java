package Ebamazon.controller;

import java.net.URL;
import java.util.Currency;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.OrdinaryUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PasswordChangeViewController {

    private CurrentSession currentSession;
    private BorderPane parent;
    private VBox navBar;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane component;

    @FXML
    private Button submitButton;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private PasswordField passwordField2;

    @FXML
    void submit(ActionEvent event) {
        //validate input
        if (!passwordField1.getText().equals("") && passwordField1.getText().equals(passwordField2.getText())){
            OrdinaryUser ou = (OrdinaryUser)currentSession.getCurUser();
            ou.changeUserPW(passwordField1.getText());
            if (currentSession.isHasComplaints()){
                //TODO LOAD COMPLAINT VIEW
            }
            else {
                //todo load home and enable navbar
                navBar.setDisable(false);
                parent.setCenter(null);
            }
        }
    }

    @FXML
    void initialize() {
        assert component != null : "fx:id=\"component\" was not injected: check your FXML file 'complaintResponseView.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'complaintResponseView.fxml'.";
        assert passwordField1 != null : "fx:id=\"passwordField1\" was not injected: check your FXML file 'complaintResponseView.fxml'.";
        assert passwordField2 != null : "fx:id=\"passwordField2\" was not injected: check your FXML file 'complaintResponseView.fxml'.";

    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public VBox getNavBar() {
        return navBar;
    }

    public void setNavBar(VBox navBar) {
        this.navBar = navBar;
    }
}
