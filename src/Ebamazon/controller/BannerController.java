package Ebamazon.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.User;
import Ebamazon.model.UserStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

    public class BannerController {

        private CurrentSession currentSession;
        private NavBarController navBarController;
        private BorderPane parent;

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private ImageView logoImageView;

        @FXML
        private HBox searchItemsHBox;

        @FXML
        private TextField searchField;

        @FXML
        private Button searchButton;

        @FXML
        private VBox loginContainerVBox;

        @FXML
        private TextField usernameField;

        @FXML
        private TextField passwordField;

        @FXML
        private HBox loginButtonsHBox;

        @FXML
        private Button loginButton;

        @FXML
        private Button applyButton;

        @FXML
        private VBox loggedInVBox;

        @FXML
        private Label usernameLabel;

        @FXML
        private Button logoutButton;

        @FXML
        void loadApplicationView(ActionEvent event) throws IOException {
            FXMLLoader applyView = new FXMLLoader();
            applyView.setLocation(getClass().getResource("../view/newUserView.fxml"));
            AnchorPane newUserScene = applyView.load();
            parent.setCenter(newUserScene);
        }

        @FXML
        void loadHome(MouseEvent event) {
            System.out.println("You clicked me!");

        }

        @FXML
        void login(ActionEvent event) {
            currentSession.setCurUser(currentSession.getCurUser().login(usernameField.getText(), passwordField.getText()));
            if (currentSession.getUserStatus() != UserStatus.GU) {
                loggedInVBox.setVisible(true);
                loginContainerVBox.setVisible(false);
                usernameLabel.setText("Hello, " + currentSession.getCurUser().getName() + "!" );
            }
            else {
                System.out.println("Username/ PW not recognized.");
            }
            navBarController.configureButtons(currentSession);
            usernameField.setText("");
            passwordField.setText("");
        }

        @FXML
        void logout(ActionEvent event){
            currentSession.setCurUser(new User());
            navBarController.configureButtons(currentSession);
            loggedInVBox.setVisible(false);
            loginContainerVBox.setVisible(true);
        }

        @FXML
        void search(ActionEvent event) {

        }

        @FXML
        void initialize() {
            assert logoImageView != null : "fx:id=\"logoImageView\" was not injected: check your FXML file 'Untitled'.";
            assert searchItemsHBox != null : "fx:id=\"searchItemsHBox\" was not injected: check your FXML file 'Untitled'.";
            assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'Untitled'.";
            assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'Untitled'.";
            assert loginContainerVBox != null : "fx:id=\"loginContainerVBox\" was not injected: check your FXML file 'Untitled'.";
            assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'Untitled'.";
            assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'Untitled'.";
            assert loginButtonsHBox != null : "fx:id=\"loginButtonsHBox\" was not injected: check your FXML file 'Untitled'.";
            assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'Untitled'.";
            assert applyButton != null : "fx:id=\"applyButton\" was not injected: check your FXML file 'Untitled'.";
            assert loggedInVBox != null : "fx:id=\"loggedInVBox\" was not injected: check your FXML file 'bannerView.fxml'.";
            assert usernameLabel != null : "fx:id=\"usernameLabel\" was not injected: check your FXML file 'bannerView.fxml'.";
            assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'bannerView.fxml'.";
        }

        public CurrentSession getCurrentSession() {
            return currentSession;
        }

        public void setCurrentSession(CurrentSession currentSession) {
            this.currentSession = currentSession;
        }

        public void setNavBarController(NavBarController navBarController) {
            this.navBarController = navBarController;
        }

        public void setParent(BorderPane parent) {
            this.parent = parent;
        }
    }


