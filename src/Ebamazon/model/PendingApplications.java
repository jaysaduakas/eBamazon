package Ebamazon.model;

import java.sql.Timestamp;

public class PendingApplications {
    private String userID;
    private Timestamp dateTimeSubmitted;
    public PendingApplications(String userID, Timestamp dateTimeSubmitted) {
        this.userID = userID;
        this.dateTimeSubmitted = dateTimeSubmitted;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public Timestamp getDateTimeSubmitted() {
        return dateTimeSubmitted;
    }
    public void setDateTimeSubmitted(Timestamp dateTimeSubmitted) {
        this.dateTimeSubmitted = dateTimeSubmitted;
    }
}
