package Ebamazon.model;

import java.sql.Timestamp;

public class Complaint {
    // Instance variables
    private OrdinaryUser sender;
    private OrdinaryUser complainee;
    private String complaint;
    private Timestamp dateTimeSent;
    private boolean alreadyJustified;
    private SuperUser superUser;

    // Getters and setters
    public OrdinaryUser getSender() {
        return sender;
    }

    public void setSender(OrdinaryUser sender) {
        this.sender = sender;
    }

    public OrdinaryUser getComplainee() {
        return complainee;
    }

    public void setComplainee(OrdinaryUser complainee) {
        this.complainee = complainee;
    }

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

    public SuperUser getSuperUser() {
        return superUser;
    }

    public void setSuperUser(SuperUser superUser) {
        this.superUser = superUser;
    }


    // TODO: Instance methods


    @Override
    public String toString() {
        return "Complaint{" +
                "sender='" + sender.getUsername() + '\'' +
                ", complainee='" + complainee.getUsername() + '\'' +
                ", complaint='" + complaint + '\'' +
                ", dateTimeSent='" + dateTimeSent + '\'' +
                ", alreadyJustified='" + alreadyJustified + '\'' +
                ", superUser='" + superUser.getUsername() + '\'' +
                '}';
    }
}
