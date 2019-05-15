package Ebamazon.controller;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.*;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
        private CheckBox showAuctions;

        @FXML
        private CheckBox showFixed;

        @FXML
        private TextField priceMin;

        @FXML
        private TextField priceMax;

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
        void login(ActionEvent event) throws IOException {
            //attempt to get user object
            currentSession.setCurUser(currentSession.getCurUser().login(usernameField.getText(), passwordField.getText()));
            //if not a guest, set greeting
            if (currentSession.getUserStatus() != UserStatus.GU) {
                loggedInVBox.setVisible(true);
                loginContainerVBox.setVisible(false);
                usernameLabel.setText("Hello, " + currentSession.getCurUser().getName() + "!" );
            }
            //incorrect login info detected
            else {
                System.out.println("Username/ PW not recognized.");
            }
            //configure the OU/GU navbar
            navBarController.configureButtons(currentSession);
            //clear user login fields
            usernameField.setText("");
            passwordField.setText("");

            //DETERMINE WHICH SCREEN TO LOAD IF USER IS OU
            if (currentSession.getCurUser().getUserStatus()==UserStatus.OU) {
                //if user needs to change password
                System.out.println(((OrdinaryUser)currentSession.getCurUser()).isSuspendedStatus());

                if (((OrdinaryUser)currentSession.getCurUser()).checkIfUsernameIsPW()){
                    FXMLLoader pwChangeLoader = new FXMLLoader();
                    pwChangeLoader.setLocation(getClass().getResource("../view/passwordChangePromptView.fxml"));
                    AnchorPane view = pwChangeLoader.load();
                    PasswordChangeViewController pcvc = pwChangeLoader.getController();
                    pcvc.setCurrentSession(currentSession);
                    pcvc.setNavBar(navBarController.getViewComponent());
                    pcvc.setParent(parent);
                    //set the view and disable the navbar
                    parent.setCenter(view);
                    navBarController.getViewComponent().setDisable(true);
                }
                //if user is banned
                //if user is suspended
                else if(((OrdinaryUser)currentSession.getCurUser()).isSuspendedStatus()){
                    FXMLLoader twoWarningsViewLoader = new FXMLLoader();
                    twoWarningsViewLoader.setLocation(getClass().getResource("../view/TwoWarningView.fxml"));
                    AnchorPane view = twoWarningsViewLoader.load();
                    TwoWarningViewController twvc = twoWarningsViewLoader.getController();
                    twvc.setCurrentSession(currentSession);
                    twvc.setUpView();
                    parent.setCenter(view);
                }

                //if user has complaints
                else if (currentSession.isHasComplaints()){
                    //load complaint view and pass it all the info
                    FXMLLoader complaintResponseLoader = new FXMLLoader();
                    complaintResponseLoader.setLocation(getClass().getResource("../view/complaintResponseView.fxml"));
                    AnchorPane view = complaintResponseLoader.load();
                    ComplaintResponseViewController crvc = complaintResponseLoader.getController();
                    crvc.setComplaints(currentSession.getComplaints());
                    crvc.setCurrentSession(currentSession);
                    crvc.setParent(parent);
                    crvc.setNavBar(navBarController.getViewComponent());
                    //set the view and disable the navbar
                    parent.setCenter(view);
                    navBarController.getViewComponent().setDisable(true);
                }
                //if user has no work to do
                else {
                    FXMLLoader searchResultsLoader = new FXMLLoader();
                    searchResultsLoader.setLocation(getClass().getResource("../view/searchResultsView.fxml"));
                    AnchorPane view = searchResultsLoader.load();
                    SearchResultsViewController srvc = searchResultsLoader.getController();
                    srvc.setCurrentSession(currentSession);
                    if (currentSession.getUserStatus()==UserStatus.OU) {
                        SearchParameters sp = generateSearchParametersFromUser();
                        try {
                            srvc.setSp(sp);
                            srvc.getBannerLabel().setText("Auctions For You!");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    parent.setCenter(view);
                }
            }
            else if (currentSession.getCurUser().getUserStatus()==UserStatus.SU){
                FXMLLoader superNavBarView = new FXMLLoader();
                superNavBarView.setLocation(getClass().getResource("../view/superUserNavBarView.fxml"));
                VBox navBar = superNavBarView.load();
                SuperUserNavBarViewController snvc = superNavBarView.getController();
                snvc.setCurrentSession(currentSession);
                snvc.setParent(parent);
                parent.setLeft(navBar);
                parent.setCenter(null);

            }
        }

        @FXML
        void logout(ActionEvent event) throws IOException {
            currentSession.setCurUser(new User());

            //reset navbar
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/navBarView.fxml"));
            VBox view = loader.load();
            parent.setLeft(view);
            navBarController = loader.getController();
            navBarController.setCurrentSession(currentSession);
            navBarController.setParent(parent);

            navBarController.configureButtons(currentSession);
            loggedInVBox.setVisible(false);
            loginContainerVBox.setVisible(true);

            //intialize homeview controller and view
            parent.setCenter(null);
        }

        @FXML
        void search(ActionEvent event) throws IOException, SQLException {
            SearchParameters sp = new SearchParameters(searchField.getText());
            if (showAuctions.isSelected()) sp.setShowAuction(true);
            else sp.setShowAuction(false);
            if (showFixed.isSelected()) sp.setShowFixed(true);
            else sp.setShowFixed(false);
            if (!priceMin.getText().equals("")) sp.setMinPrice(BigDecimal.valueOf(Double.parseDouble(priceMin.getText())));
            if (!priceMax.getText().equals("")) sp.setMaxPrice(BigDecimal.valueOf(Double.parseDouble(priceMax.getText())));

            FXMLLoader searchResultsLoader = new FXMLLoader();
            searchResultsLoader.setLocation(getClass().getResource("../view/searchResultsView.fxml"));
            AnchorPane view = searchResultsLoader.load();
            SearchResultsViewController srvc = searchResultsLoader.getController();
            srvc.setCurrentSession(currentSession);
            srvc.setSp(sp);
            parent.setCenter(view);
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
            priceMin.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
                        priceMin.setText(oldValue);
                    }
                }
            });
            priceMax.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
                        priceMax.setText(oldValue);
                    }
                }
            });
            startLoop();
        }

        private void startLoop(){
            AnimationTimer at = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (!showAuctions.isSelected() && !showFixed.isSelected()){
                        showFixed.setSelected(true);
                        showAuctions.setSelected(true);
                    }
                }
            };
            at.start();
        }

        private SearchParameters generateSearchParametersFromUser() { //Marissa added this!
            OrdinaryUser ou = (OrdinaryUser) currentSession.getCurUser();
            ArrayList<UserKeyword> ouKeywords = new ArrayList<>();
            ouKeywords = ou.getKeywords();
            String keywords = "";
            for (UserKeyword uk : ouKeywords){
                keywords += uk.getKeyword() + " ";
            }
            SearchParameters sp = new SearchParameters(keywords);
            sp.setShowAuction(true);
            sp.setShowFixed(true);
            return sp;
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


