package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.OrdinaryUserDAO;
import Ebamazon.model.DataAccessLayer.PendingApplicationDAO;

import java.sql.Timestamp;

public class OrdinaryUser extends User{
    private String address;
    private String stateID;
    private String cc;
    private String phone;
    private boolean approvedStatus;
    private boolean bannedStatus;
    private boolean VIPStatus;
    private Timestamp dateTimeRegistered;

    public OrdinaryUser(){
        setUserStatus(UserStatus.OU);
    }

    public boolean updateUserInfo(){
        return OrdinaryUserDAO.setUser(this);
    }


    public boolean insertUserInfo(){
        //if user with object's username does not exist in the OU table, insert it with the values specified by
        //this object.
        if (OrdinaryUserDAO.getOrdinaryUser(getUsername()) == null) {
            OrdinaryUserDAO.submitNewUser(this);
            PendingApplicationDAO.insertNewUser(getUsername());
            return true;
        }
        return false;
    }

    public boolean changeUserPW(String pw){
        return OrdinaryUserDAO.setUserPassword(this, pw);
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
                "username='" + getUsername() + '\'' +
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