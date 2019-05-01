package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.OrdinaryUserDAO;
import Ebamazon.model.DataAccessLayer.PendingApplicationDAO;

import java.sql.Timestamp;

public class OrdinaryUser extends User{
    private String username;
    private String address;
    private String stateID;
    private String cc;
    private String phone;
    private boolean approvedStatus;
    private boolean bannedStatus;
    private boolean VIPStatus;
    private Timestamp dateTimeRegistered;


    public boolean updateUserInfo(){
        return OrdinaryUserDAO.setUser(this);
    }

    public boolean insertUserInfo(){
        //if user with object's username does not exist in the OU table, insert it with the values specified by
        //this object.
        if (OrdinaryUserDAO.getOrdinaryUser(username) == null) {
            OrdinaryUserDAO.submitNewUser(this);
            PendingApplicationDAO.insertNewUser(username);
            return true;
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(boolean approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public boolean isBannedStatus() {
        return bannedStatus;
    }

    public void setBannedStatus(boolean bannedStatus) {
        this.bannedStatus = bannedStatus;
    }

    public boolean isVIPStatus() {
        return VIPStatus;
    }

    public void setVIPStatus(boolean VIPStatus) {
        this.VIPStatus = VIPStatus;
    }

    public Timestamp getDateTimeRegistered() {
        return dateTimeRegistered;
    }

    public void setDateTimeRegistered(Timestamp dateTimeRegistered) {
        this.dateTimeRegistered = dateTimeRegistered;
    }

    @Override
    public String toString() {
        return "OrdinaryUser{" +
                "username='" + username + '\'' +
                ", name='" + getName() + '\'' +
                ", address='" + address + '\'' +
                ", stateID='" + stateID + '\'' +
                ", cc='" + cc + '\'' +
                ", phone='" + phone + '\'' +
                ", approvedStatus=" + approvedStatus +
                ", bannedStatus=" + bannedStatus +
                ", VIPStatus=" + VIPStatus +
                ", dateTimeRegistered=" + dateTimeRegistered +
                '}';
    }
}
