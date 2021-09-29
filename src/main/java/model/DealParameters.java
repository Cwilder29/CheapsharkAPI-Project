package model;

public class DealParameters {
    private Store store;
    private int upperPrice;
    private Sort sortBy;

    public DealParameters() {
    }

    public DealParameters(Store store, int upperPrice, Sort sortBy) {
        this.store = store;
        this.upperPrice = upperPrice;
        this.sortBy = sortBy;
    }

    //accessors
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

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
