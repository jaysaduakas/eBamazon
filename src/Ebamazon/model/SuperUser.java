package Ebamazon.model;

public class SuperUser extends User {

    public SuperUser(){
        setUserStatus(UserStatus.SU);
    }

    // TODO: Create class methods


    @Override
    public String toString() {
        return "SuperUser{" +
                "username='" + getUsername() + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
}
