package Ebamazon.model;


public class AuctionResult extends Auction implements Comparable {
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public int compareTo(Object o) {
        return (-1 * (Integer.compare(score, ((AuctionResult)o).getScore())));

    }
}
