package Ebamazon.model;

public class SuperUser extends User {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    // TODO: Create class methods


    @Override
    public String toString() {
        return "SuperUser{" +
                "username='" + username + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
}
