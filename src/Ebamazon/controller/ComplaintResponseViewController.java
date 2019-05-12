package Ebamazon.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.Complaint;
import Ebamazon.model.CurrentSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ComplaintResponseViewController {

    private CurrentSession currentSession;
    private ArrayList<Complaint> complaints;
    private int complaintIndex=0;
    private BorderPane parent;
    private VBox navBar;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane component;

    @FXML
    private TextArea complaintText;

    @FXML
    private TextArea responseText;

    @FXML
    private Button submitButton;

    @FXML
    void submit(ActionEvent event) {
        //if the user has responded to the complaint...
        if (!responseText.getText().equals("")) {
            //get the current comaplint object
            Complaint curComplaint = complaints.get(complaintIndex);
            //set response
            curComplaint.setComplaineeResponse(responseText.getText());
            //update in the database
            currentSession.updateComplaint(curComplaint);

            //if there are more complaints...
            if (complaintIndex < complaints.size() - 1) {
                //increment index
                complaintIndex++;
                //clear screen
                responseText.setText("");
                //set label
                setCurComplaint();
            }
            //if there are no more complaints
            else {
                if (true) { //TODO ADD NEXT CHECK IN THE LIST OF THINGS THE USER MAY HAVE TO DO WHEN LOGGING IN, in this case assume everythings good to go
                    System.out.println();
                    navBar.setDisable(false);
                    currentSession.setHasComplaints(false);
                }
            }
        }
    }

    @FXML
    void initialize() {
        assert component != null : "fx:id=\"component\" was not injected: check your FXML file 'complaintResponseView.fxml'.";
        assert complaintText != null : "fx:id=\"complaintText\" was not injected: check your FXML file 'complaintResponseView.fxml'.";
        assert responseText != null : "fx:id=\"responseText\" was not injected: check your FXML file 'complaintResponseView.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'complaintResponseView.fxml'.";

    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }

    public ArrayList<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(ArrayList<Complaint> complaints) {
        this.complaints = complaints;
        setCurComplaint();
    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    private void setCurComplaint(){
        complaintText.setText(complaints.get(complaintIndex).getComplaint());
    }

    public VBox getNavBar() {
        return navBar;
    }

    public void setNavBar(VBox navBar) {
        this.navBar = navBar;
    }
}
