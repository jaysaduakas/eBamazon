package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.WarningDAO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Warning {
    // Instance variables
    private OrdinaryUser ordinaryUser;
    private SuperUser superUser;
    private String reason;
    private Timestamp dateTimeIssued;


    //inserts a warning
    public boolean insertWarning(){
        if (WarningDAO.insertWarning(this)){
            Message message = new Message();
            message.setSubject("You Have Received A Warning");
            message.setSender("Mod Team");
            message.setReceiver(ordinaryUser.getUsername());
            message.setMessageContent("You Have Received A Warning For the Following Reason:\n\n" + reason + "\n\nIf you receive 2 or more Warnings you will be suspended.");
            ordinaryUser.sendMessage(message);
            return true;
            //call to check if need to be suspended
        }
        return false;
    }

    public static boolean shouldBeBanned(String username){
        ArrayList<Warning> warnings = WarningDAO.getWarningsByUser(username);
        return (warnings.size()%2==0) && (warnings.size()!=0);
    }
    public static boolean hasWarnings(String username){
        return (!WarningDAO.getWarningsByUser(username).isEmpty());
    }

    //need method to get warningsbyusername;

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
