package model;

public class DealParameters {
    private int upperPrice;

    public DealParameters() {
    }

    public DealParameters(int upperPrice) {
        this.upperPrice = upperPrice;
    }

    //accessors
    public int getUpperPrice() {
        return upperPrice;
    }

    public void setUpperPrice(int upperPrice) {
        this.upperPrice = upperPrice;
    }
}
