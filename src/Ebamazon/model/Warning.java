package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.WarningDAO;

import java.sql.Timestamp;

public class Warning {
    // Instance variables
    private OrdinaryUser ordinaryUser;
    private SuperUser superUser;
    private String reason;
    private Timestamp dateTimeIssued;


    //inserts a warning
    public boolean insertWarning(){
        return WarningDAO.insertWarning(this);
    }


    // Getters and setters
    public OrdinaryUser getOrdinaryUser() {
        return ordinaryUser;
    }

    public void setOrdinaryUser(OrdinaryUser ordinaryUser) {
        this.ordinaryUser = ordinaryUser;
    }

    public SuperUser getSuperUser() {
        return superUser;
    }

    public void setSuperUser(SuperUser superUser) {
        this.superUser = superUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getDateTimeIssued() {
        return dateTimeIssued;
    }

    public void setDateTimeIssued(Timestamp dateTimeIssued) {
        this.dateTimeIssued = dateTimeIssued;
    }


    // TODO: Instance methods


    @Override
    public String toString() {
        return "Warning{" +
                "ordinaryUser='" + ordinaryUser.getUsername() + '\'' +
                ", superUser='" + superUser.getUsername() + '\'' +
                ", reason='" + reason + '\'' +
                ", dateTimeIssued='" + dateTimeIssued + '\'' +
                '}';
    }
}
