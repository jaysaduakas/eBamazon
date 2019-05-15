package Ebamazon.model;


public class AuctionResult extends Auction implements Comparable {
    private int score;
    private int swapValue;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSwapValue() {
        return swapValue;
    }

    public void setSwapValue() {
        this.swapValue = (roundValue()).intValue();
    }



    private Long roundValue(){
        //if(true) {
            Long val = Math.round(Ratings.getAverageRating(getOrdinaryUser().getUsername()));
            return val;
        //}
    }

    public void swapScores(){
        int temp = score;
        score = swapValue;
        swapValue = temp;
    }

    @Override
    public int compareTo(Object o) {
        return (-1 * (Integer.compare(score, ((AuctionResult)o).getScore())));

    }
}
