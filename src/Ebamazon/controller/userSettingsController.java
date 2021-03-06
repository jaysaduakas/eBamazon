package Ebamazon.controller;

import Ebamazon.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class userSettingsController {

    private BorderPane parent;
    private CurrentSession currentSession;
    private boolean tabooFound;

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
    private TextArea keywordTextArea;

    @FXML
    private ComboBox<State> stateID;

    @FXML
    private VBox keywordComponentVBox;

    @FXML
    private Label updatedSuccess;

    @FXML
    void submitUserSettings(ActionEvent event) throws IOException {
        if (currentSession.getUserStatus() == UserStatus.OU) {
            OrdinaryUser ou = (OrdinaryUser) currentSession.getCurUser();
            if (!nameField.getText().equals("")) {
                ou.setName(nameField.getText());
                nameField.setText("");
            }
            if (!addressField.getText().equals("")) {
                ou.setAddress(addressField.getText());
                addressField.setText("");
            }
            if (!phoneNumberField.getText().equals("")) {
                ou.setPhone(phoneNumberField.getText());
                phoneNumberField.setText("");
            }
            if (!creditcardField.getText().equals("")) {
                ou.setCc(creditcardField.getText());
                creditcardField.setText("");
            }
            if (stateID.getValue()!=null) {
                ou.setStateID(stateID.getValue().getAbbreviation());
                stateID.setValue(null);
            }
            if (ou.updateUserInfo()) {
                //TODO display this as a label in JFX
                updatedSuccess.setVisible(true);
                currentSession.setCurUser(ou);
            } else {
                updatedSuccess.setVisible(false);
            }
            if (!passwordField.getText().equals("")) {
                if (ou.changeUserPW(passwordField.getText())){
                    passwordField.setText("");
                    System.out.println("Password updated in database");
                }
            }
            if (!keywordTextArea.getText().equals("")) {
                scrubKeywords();                            // Censor all taboo words
                ArrayList<String> keywords = parseKeywordInput();
                keywords = removeTaboo(keywords);           // Remove taboo words
                for (String s : keywords) {
                    UserKeyword u = new UserKeyword();
                    u.setUsername(currentSession.getCurUser().getUsername());
                    u.setKeyword(s);
                    ou.submitKeyword(u);
                }
                keywordTextArea.setText("");
                keywordComponentVBox.getChildren().clear();
                populateKeywordsList();
            }
        }
    }

    private boolean scrubKeywords() {
        boolean tabooFound = false;
        try {
            InputScrubber is = new InputScrubber();
            keywordTextArea.setText(is.scrubInput(keywordTextArea.getText()));
            if(is.hasTaboo()) {tabooFound = true;}
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return tabooFound;
    }

    private ArrayList<String> removeTaboo(ArrayList<String> list) {
        String s;
        // Remove taboo words, i.e., all words that contain an asterisk (*)
        for (int i = (list.size() - 1); i >= 0; i--) {
            s = list.get(i);
            if (s.contains("*")) {
                list.remove(s);
            }
        }
        return list;
    }

    public void deleteKeywordItem(UserKeywordComponentViewController ukcvc) {
        OrdinaryUser ordinaryUser = (OrdinaryUser)currentSession.getCurUser();
        ordinaryUser.deleteKeyword(ukcvc.getUserKeyword());
        keywordComponentVBox.getChildren().remove(ukcvc.getKeywordView());
    }

    @FXML
    void initialize() {
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert phoneNumberField != null : "fx:id=\"phoneNumberField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert creditcardField != null : "fx:id=\"creditcardField\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'userSettingsView.fxml'.";
        stateID.getItems().setAll(State.values());
    }

    private ArrayList<String> parseKeywordInput() {
        String [] userKeyword = keywordTextArea.getText().split(" ");
        return new ArrayList<String>(Arrays.asList(userKeyword));
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public void setCurrentSession(CurrentSession currentSession) throws IOException {
        this.currentSession = currentSession;
        populateKeywordsList();
    }

    private void populateKeywordsList() throws IOException {
        OrdinaryUser ou =(OrdinaryUser) currentSession.getCurUser();
        ArrayList<UserKeyword> keywords = ou.getKeywords();
        for (UserKeyword uk : keywords){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UserKeywordComponentView.fxml"));
            HBox keywordView = loader.load();
            keywordComponentVBox.getChildren().add(keywordView);
            UserKeywordComponentViewController ukcvc = loader.getController();
            ukcvc.setUserKeyword(uk);
            ukcvc.setParent(this);
        }
    }
}
