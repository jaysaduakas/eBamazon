package Ebamazon.model;


public class CurrentSession {

    private User curUser;
    private boolean isBanned;
    private UserStatus userStatus;

    public CurrentSession(){
        curUser = new User();
        isBanned = false;
        userStatus = UserStatus.GU;
    }

    public CurrentSession(User user){
        setCurUser(user);
    }

    public void setCurUser(User user){
        curUser = user;
        if (user instanceof OrdinaryUser){
            userStatus = UserStatus.OU;
            isBanned = ((OrdinaryUser) user).isBannedStatus();
        }
        else if (user instanceof SuperUser){
            userStatus = UserStatus.SU;
            isBanned = false;
        }
        else {
            userStatus = UserStatus.GU;
            isBanned = false;
        }
    }

    public User getCurUser(){
        return curUser;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }
}
