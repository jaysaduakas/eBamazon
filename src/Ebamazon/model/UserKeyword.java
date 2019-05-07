package Ebamazon.model;

public class UserKeyword {
    private String username;
    private String keyword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "UserKeyword{" +
                "username='" + username + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
