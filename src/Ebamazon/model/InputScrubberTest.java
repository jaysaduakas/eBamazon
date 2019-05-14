package Ebamazon.model;

import java.sql.SQLException;

public class InputScrubberTest { // Test code for InputScrubber class.

    public static void main(String[] args) {
        String input = "TEST:: One..FARK-fark #two%, \tMotherfarker? fARKShlub@SHLUBBER; $three.";
        System.out.println(input);
        try {
            InputScrubber test = new InputScrubber();

            String output = test.scrubInput(input);
            System.out.println(output);
            System.out.println("hasTaboo() = " + test.hasTaboo());
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
