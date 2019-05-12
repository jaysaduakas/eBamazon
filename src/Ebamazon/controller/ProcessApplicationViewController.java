package Ebamazon.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.OrdinaryUser;
import Ebamazon.model.PendingApplications;
import Ebamazon.model.SuperUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ProcessApplicationViewController {

    private CurrentSession currentSession;
    private OrdinaryUser applicant;
    private PendingApplications pending;
    private ToggleGroup toggle;

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
    void submitNewUser(ActionEvent event) throws SQLException {
        SuperUser curUesr = (SuperUser)currentSession.getCurUser();
        if (applicant!=null) {
            if (acceptRadioButton.isSelected()) {
                applicant.setApprovedStatus(true);
                applicant.updateUserInfo();
                curUesr.removeApplication(pending);
                getApplication();
            }
            if (denyRadioButton.isSelected()) {
                curUesr.removeApplication(pending);
                curUesr.removeUser(applicant);
                getApplication();
            }
        }
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
        toggle = new ToggleGroup();
        acceptRadioButton.setToggleGroup(toggle);
        denyRadioButton.setToggleGroup(toggle);
    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
        try {
            getApplication();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getApplication() throws SQLException {
        SuperUser curUser = (SuperUser)currentSession.getCurUser();
        ArrayList<PendingApplications> applications = curUser.getPendingApplications();
        numberRemainingLabel.setText(Integer.toString(applications.size()));
        if (applications.size()!=0) {
            pending = applications.get(0);
            applicant = currentSession.getUserByUsername(applications.get(0).getUserID());
            usernameField.setText(applicant.getUsername());
            nameField.setText(applicant.getName());
            addressField.setText(applicant.getAddress());
            stateField.setText(applicant.getStateID());
            phoneNumberField.setText(applicant.getPhone());
            if (applicant.getCc().length() == 16) {
                creditcardField.setText("**** **** **** " + applicant.getCc().substring(applicant.getCc().length() - 4));
            } else {
                creditcardField.setText("USER'S CARD IS NOT IN VALID FORMAT");
            }
        } else {
            clearAllFields();;
        }
    }

    private void clearAllFields(){
        pending=null;
        applicant=null;
        usernameField.setText("");
        nameField.setText("");
        addressField.setText("");
        stateField.setText("");
        phoneNumberField.setText("");
        creditcardField.setText("");
    }
}
