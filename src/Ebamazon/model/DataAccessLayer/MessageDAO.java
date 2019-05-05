package Ebamazon.model.DataAccessLayer;

import Ebamazon.model.Message;

import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;

public class MessageDAO {

    public static Message getMessage(String receiver, String sender, Timestamp dateTimeSent){
        try{
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM Message WHERE " +
                    "sender=\"" + sender + "\" AND " +
                    "receiver=\"" + receiver + "\" AND " +
                    "dateTimeSent=\"" + dateTimeSent + "\"";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()){
                Message message = new Message();
                message.setSender(rs.getString("sender"));
                message.setReceiver(rs.getString("receiver"));
                message.setSubject(rs.getString("subject"));
                message.setMessageContent(rs.getString("messageContent"));
                message.setDateTimeSent(rs.getTimestamp("dateTimeSent"));
                return message;
            }
        }
        catch (SQLException e){
            System.out.println("Error retrieving message");
        }
        return null;
    }


    public static Timestamp sendMessage(Message message){
        try{
            Calendar calendar = Calendar.getInstance();
            Timestamp sent = new Timestamp(1000* (calendar.getTime().getTime()/1000));
            System.out.println(sent);

            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO Message VALUES (\"" +
                    message.getSender() + "\", \"" +
                    message.getReceiver() + "\", \"" +
                    message.getSubject() + "\", \"" +
                    message.getMessageContent() + "\", \"" +
                    sent + "\")";
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            return sent;
        }
        catch (SQLException e){
            System.out.println("Error sending message");

        }
        return null;
    }

    //todo Sort messages retrieved by date order
    public static ArrayList<Message> getAllMessages(String user){
        ArrayList<Message> messages = new ArrayList<>();
        try{
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM Message WHERE " +
                    "receiver=\"" + user + "\"";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Message message = new Message();
                message.setSender(rs.getString("sender"));
                message.setReceiver(rs.getString("receiver"));
                message.setSubject(rs.getString("subject"));
                message.setMessageContent(rs.getString("messageContent"));
                message.setDateTimeSent(rs.getTimestamp("dateTimeSent"));
                messages.add(message);
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred retrieving all messages.");
        }
        return messages;
    }

    public static void deleteMessage(String sender, String recipient, Message message){
        try{
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM Message WHERE " +
                    "receiver=\"" + recipient + "\" AND " +
                    "sender =\"" + sender + "\" AND " +
                    "dateTimeSent=\"" + message.getDateTimeSent() + "\"";
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException e){
            System.out.println("An error occurred deleting the message.");
        }
    }


    public static void main(String[] args) {
        Message message = new Message();
        message.setSender("Jake");
        message.setReceiver("Not Jake");
        message.setMessageContent("Hey man, how are you?  This is a message I'm sending you.  Pretty cool, huh?");
        message.setSubject("This is a message");
        Timestamp sent = sendMessage(message);
        message = getMessage("Not Jake","Jake", sent);
        System.out.println(message.toString());

        for (Message m : getAllMessages("Not Jake")){
            System.out.println(m.toString());
        }
    }
}
