package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.PendingAppealDAO;

import java.sql.Timestamp;

public class PendingAppeal {
    private String username;
    private String text;
    private Timestamp dateTimeAppealed;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDateTimeAppealed() {
        return dateTimeAppealed;
    }

    public void setDateTimeAppealed(Timestamp dateTimeAppealed) {
        this.dateTimeAppealed = dateTimeAppealed;
    }

    public boolean insertAppeal(){
        return PendingAppealDAO.insertNewAppeal(this);
    }


}

