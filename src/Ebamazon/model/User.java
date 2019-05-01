package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.DBConnection;
import Ebamazon.model.DataAccessLayer.OrdinaryUserDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
    private String name = "guest";

    public User login(String username, String pw){
        if (verifyOrdinaryLogin(username,pw))
            return OrdinaryUserDAO.getOrdinaryUser(username);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
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
}
