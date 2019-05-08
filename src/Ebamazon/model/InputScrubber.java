package Ebamazon.model;

import java.util.ArrayList;

public class InputScrubber {

    private ArrayList<String> tabooWords;

    public InputScrubber(){
        tabooWords = new ArrayList<>();
        tabooWords.add("Shlub");
        tabooWords.add("fark");
    }

    public String scrubInput(String input){
        String returnVal = input;
        //scrub input of bad words

        return returnVal;
    }

}
