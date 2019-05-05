package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.DBConnection;
import Ebamazon.model.DataAccessLayer.MessageDAO;
import Ebamazon.model.DataAccessLayer.OrdinaryUserDAO;

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
        if (verifyOrdinaryLogin(username,pw))
            return OrdinaryUserDAO.getOrdinaryUser(username);
        return this;
    }
    private boolean verifyOrdinaryLogin(String username, String pw){
        boolean truthFlag = false;
        try {
            String query = "SELECT * FROM OrdinaryUser WHERE username=\"" + username + "\" AND password=\"" + pw + "\" AND approvedStatus=1";
            Connection con = DBConnection.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()){
                truthFlag=true;
            }
            con.close();
        }
        catch(Exception e){
            System.out.println("Error verifying login");
        }
        return truthFlag;
    }

    //user messaging functions
    public ArrayList<Message> getMessages(){
        return MessageDAO.getAllMessages(this.getUsername());
    }
    public void sendMessage(Message m) { MessageDAO.sendMessage(m);}
    public void deleteMessage(Message m){
        MessageDAO.deleteMessage(m.getSender(), getUsername(), m);
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
