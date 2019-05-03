package Ebamazon.model;

import java.sql.Timestamp;

public class Friends {
    private String SuggestingFriendlD;
    private String ConfirmingFriendID;
    private boolean FriendshipConfirmed;
    private Timestamp DateTimeConfirmed;
    public Friends(String suggestingFriendlD, String confirmingFriendID, boolean friendshipConfirmed, Timestamp dateTimeConfirmed) {
        SuggestingFriendlD = suggestingFriendlD;
        ConfirmingFriendID = confirmingFriendID;
        FriendshipConfirmed = friendshipConfirmed;
        DateTimeConfirmed = dateTimeConfirmed;
    }
    public String getSuggestingFriendlD() {
        return SuggestingFriendlD;
    }
    public void setSuggestingFriendlD(String suggestingFriendlD) {
        SuggestingFriendlD = suggestingFriendlD;
    }
    public String getConfirmingFriendID() {
        return ConfirmingFriendID;
    }
    public void setConfirmingFriendID(String confirmingFriendID) {
        ConfirmingFriendID = confirmingFriendID;
    }
    public boolean isFriendshipConfirmed() {
        return FriendshipConfirmed;
    }
    public void setFriendshipConfirmed(boolean friendshipConfirmed) {
        FriendshipConfirmed = friendshipConfirmed;
    }
    public Timestamp getDateTimeConfirmed() {
        return DateTimeConfirmed;
    }
    public void setDateTimeConfirmed(Timestamp dateTimeConfirmed) {
        DateTimeConfirmed = dateTimeConfirmed;
    }
}
