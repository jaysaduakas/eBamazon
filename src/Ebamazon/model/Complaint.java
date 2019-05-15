package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.ComplaintDAO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Complaint {
    // Instance variables
    private String sender;
    private String complainee;
    private String complaint;
    private Timestamp dateTimeSent;
    private boolean alreadyJustified;
    private String superUser;
    private boolean complaineeResponded;
    private String complaineeResponse;



    public static ArrayList<Complaint> getUnjustifiedComplaints() { return ComplaintDAO.getUnjustifiedComplaints();}
    public boolean resolveComplaint(boolean accept) {return ComplaintDAO.resolveComplaint(this, accept);}

    // Getters and setters

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Timestamp getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(Timestamp dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }

    public boolean isAlreadyJustified() {
        return alreadyJustified;
    }

    public void setAlreadyJustified(boolean alreadyJustified) {
        this.alreadyJustified = alreadyJustified;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getComplainee() {
        return complainee;
    }

    public void setComplainee(String complainee) {
        this.complainee = complainee;
    }

    public String getSuperUser() {
        return superUser;
    }

    public void setSuperUser(String superUser) {
        this.superUser = superUser;
    }

    public boolean isComplaineeResponded() {
        return complaineeResponded;
    }

    public void setComplaineeResponded(boolean complaineeResponded) {
        this.complaineeResponded = complaineeResponded;
    }

    public String getComplaineeResponse() {
        return complaineeResponse;
    }

    public void setComplaineeResponse(String complaineeResponse) {
        this.complaineeResponse = complaineeResponse;
    }

    // TODO: Instance methods


    @Override
    public String toString() {
        return "Complaint{" +
                "sender='" + sender + '\'' +
                ", complainee='" + complainee + '\'' +
                ", complaint='" + complaint + '\'' +
                ", dateTimeSent=" + dateTimeSent +
                ", alreadyJustified=" + alreadyJustified +
                ", superUser='" + superUser + '\'' +
                '}';
    }
}
