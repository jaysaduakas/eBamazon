package Ebamazon.model;

import java.sql.Timestamp;

public class Taboo {
    // Instance variables
    private String word;
    private SuperUser superUser;
    private Timestamp dateTimeBanned;

    // Getters and setters
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public SuperUser getSuperUser() {
        return superUser;
    }

    public void setSuperUser(SuperUser superUser) {
        this.superUser = superUser;
    }

    public Timestamp getDateTimeBanned() {
        return dateTimeBanned;
    }

    public void setDateTimeBanned(Timestamp dateTimeBanned) {
        this.dateTimeBanned = dateTimeBanned;
    }


    // TODO: Instance methods


    @Override
    public String toString() {
        return "Taboo{" +
                "word='" + word + '\'' +
                ", superUser='" + superUser.getUsername() + '\'' +
                ", dateTimeBanned='" + dateTimeBanned + '\'' +
                '}';
    }
}
