package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private String name = "guest";
    private String username = null;
    private UserStatus userStatus = UserStatus.GU;

    //user login functions
    public User login(String username, String pw){
        if (OrdinaryUserDAO.verifyOrdinaryLogin(username,pw))
            return OrdinaryUserDAO.getOrdinaryUser(username);
        if (SuperUserDAO.checkPassword(username, pw))
            return SuperUserDAO.getSuperUser(username);
        return this;
    }


    //user messaging functions
    public ArrayList<Message> getMessages(){
        return MessageDAO.getAllMessages(this.getUsername());
    }
    public void sendMessage(Message m) { MessageDAO.sendMessage(m);}
    public void deleteMessage(Message m){
        MessageDAO.deleteMessage(m.getSender(), getUsername(), m);
    }

    //user complaining functions
    public void lodgeComplaint(Complaint c){
        ComplaintDAO.submitComplaint(c);
    }




    //utility functions
    public boolean verifyUserExists(String username){
        return OrdinaryUserDAO.checkExists(username) || SuperUserDAO.checkExists(username);
    }

    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
