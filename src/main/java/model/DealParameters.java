package model;

public class DealParameters {
    private int upperPrice;
    private Sort sortBy;

    public DealParameters() {
    }

    public DealParameters(int upperPrice, Sort sortBy) {
        this.upperPrice = upperPrice;
        this.sortBy = sortBy;
    }

    //accessors
    public int getUpperPrice() {
        return upperPrice;
    }

    public void setUpperPrice(int upperPrice) {
        this.upperPrice = upperPrice;
    }

    public Sort getSortBy() {
        return sortBy;
    }

    public void setSortBy(Sort sortBy) {
        this.sortBy = sortBy;
    }
}
