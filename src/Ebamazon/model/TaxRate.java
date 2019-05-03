package Ebamazon.model;

public class TaxRate {
    private State state;
    private double rate;
    public TaxRate(State state, double rate) {
        this.state = state;
        this.rate = rate;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }
}
