package Ebamazon.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.Complaint;
import Ebamazon.model.CurrentSession;
import Ebamazon.model.DataAccessLayer.ComplaintDAO;
import Ebamazon.model.SuperUser;
import Ebamazon.model.Warning;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ProcessComplaintViewController {

    private CurrentSession currentSession;
    private Complaint curComplaint;
    private ToggleGroup tg;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField complainee;

    @FXML
    private TextField complainer;

    @FXML
    private TextArea complaint;

    @FXML
    private TextArea response;

    @FXML
    private RadioButton acceptButton;

    @FXML
    private ToggleGroup Type;

    @FXML
    private RadioButton denyButton;

    @FXML
    private ToggleGroup Type1;

    @FXML
    private Button submtButton;

    @FXML
    void submit(ActionEvent event) {
        if (acceptButton.isSelected()){
            curComplaint.setSuperUser(currentSession.getCurUser().getUsername());
            curComplaint.resolveComplaint(true);
            if (Complaint.shouldBeWarned(curComplaint.getComplainee())){
                Warning warning = new Warning();
                warning.setOrdinaryUser(currentSession.getUserByUsername(curComplaint.getComplainee()));
                warning.setReason("You have received two justified complaints.");
                warning.setSuperUser((SuperUser)currentSession.getCurUser());
                warning.insertWarning();
            }
            setUpView();
        }
        else if (denyButton.isSelected()){
            curComplaint.setSuperUser(currentSession.getCurUser().getUsername());
            curComplaint.resolveComplaint(false);
            setUpView();
        }
    }

    @FXML
    void initialize() {
        assert complainee != null : "fx:id=\"complainee\" was not injected: check your FXML file 'processComplaintView.fxml'.";
        assert complainer != null : "fx:id=\"complainer\" was not injected: check your FXML file 'processComplaintView.fxml'.";
        assert complaint != null : "fx:id=\"complaint\" was not injected: check your FXML file 'processComplaintView.fxml'.";
        assert response != null : "fx:id=\"response\" was not injected: check your FXML file 'processComplaintView.fxml'.";
        assert acceptButton != null : "fx:id=\"acceptButton\" was not injected: check your FXML file 'processComplaintView.fxml'.";
        assert Type != null : "fx:id=\"Type\" was not injected: check your FXML file 'processComplaintView.fxml'.";
        assert denyButton != null : "fx:id=\"denyButton\" was not injected: check your FXML file 'processComplaintView.fxml'.";
        assert Type1 != null : "fx:id=\"Type1\" was not injected: check your FXML file 'processComplaintView.fxml'.";
        assert submtButton != null : "fx:id=\"submtButton\" was not injected: check your FXML file 'processComplaintView.fxml'.";
        tg=new ToggleGroup();
        denyButton.setToggleGroup(tg);
        acceptButton.setToggleGroup(tg);
        setUpView();
    }

    private void setUpView(){
        ArrayList<Complaint> complaints = Complaint.getUnjustifiedComplaints();
        if (complaints.size() > 0){
            curComplaint = complaints.get(0);
            complainee.setText(curComplaint.getComplainee());
            complainer.setText(curComplaint.getSender());
            complaint.setText(curComplaint.getComplaint());
            response.setText((curComplaint.getComplaineeResponse()));
        }
        else{
            clearAllFields();
        }
    }

    private void clearAllFields(){
        complaint.setText("");
        complainer.setText("");
        complainee.setText("");
        response.setText("");
        curComplaint = null;
    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }
}
