package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.OrdinaryUserDAO;
import Ebamazon.model.DataAccessLayer.PendingApplicationDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SuperUser extends User {

    public SuperUser(){
        setUserStatus(UserStatus.SU);
    }

    // TODO: Create class methods

    public boolean removeApplication(PendingApplications p) {return PendingApplicationDAO.removeApplication(p);}
    public ArrayList<PendingApplications> getPendingApplications() throws SQLException {return PendingApplicationDAO.getAllPending();}
    public boolean removeUser(OrdinaryUser ou) {
        return OrdinaryUserDAO.removeUser(ou);
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "username='" + getUsername() + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
}
