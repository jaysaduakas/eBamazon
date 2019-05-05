package Ebamazon.model;

import java.sql.Timestamp;

public class Message {
    private String sender;
    private String receiver;
    private String subject;
    private String messageContent;
    private Timestamp dateTimeSent;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(Timestamp dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", subject='" + subject + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", dateTimeSent=" + dateTimeSent +
                '}';
    }
}
