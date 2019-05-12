package Ebamazon.model;

import Ebamazon.model.DataAccessLayer.TabooDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class InputScrubber {

    private ArrayList<String> tabooWords;
    private boolean tabooFound;

    public InputScrubber() throws SQLException {
        tabooWords = TabooDAO.getTabooWords();
        tabooFound = false;
        }

    public boolean hasTaboo() {
        return tabooFound;
    }

    public String scrubInput(String input) {
        String output = input;
        String censor = "";
        // Scrub out taboo words by replacing with asterisks.
        for (String word : tabooWords) {
            censor = "";
            for (int i=0; i<word.length(); i++) {
                censor += "*";
            }
            output = replaceAll(word, censor, input, output);
        }
        return output;
    }

    // This function modified from
    // https://stackoverflow.com/questions/5054995/how-to-replace-case-insensitive-literal-substrings-in-java
    private String replaceAll(String findtxt, String replacetxt, String str, String strOut) {
        tabooFound = false;
        if (str == null) {
            return null;
        }
        if (findtxt == null || findtxt.length() == 0) {
            return str;
        }
        if (findtxt.length() > str.length()) {
            return str;
        }
        int counter = 0;
        String thesubstr = "";
        while ((counter < str.length()) && (str.substring(counter).length() >= findtxt.length())) {
            thesubstr = str.substring(counter, counter + findtxt.length());
            if (thesubstr.equalsIgnoreCase(findtxt)) {
                strOut = strOut.substring(0, counter) + replacetxt + strOut.substring(counter + findtxt.length());
                // Failing to increment counter by replacetxt.length() leaves you open
                // to an infinite-replacement loop scenario: Go to replace "a" with "aa" but
                // increment counter by only 1 and you'll be replacing 'a's forever.
                counter += replacetxt.length();
                tabooFound = true;
            } else {
                counter++; // No match so move on to next character to check for a findtxt string match.
            }
        }
        return strOut;
    }

    public static void main(String[] args) throws SQLException {
        InputScrubber is = new InputScrubber();
        System.out.println(is.scrubInput("fArk schlub bob granny"));
    }
}
