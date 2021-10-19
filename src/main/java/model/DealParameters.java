package model;

public class DealParameters {
    private Store store;
    private int upperPrice;
    private Sort sortBy;
    private Boolean onlyAAA;
    private Boolean onlySale;
    private int MetacriticRating;
    private int SteamRating;

    public DealParameters() {
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

    public Boolean getOnlyAAA() {
        return onlyAAA;
    }

    public void setOnlyAAA(Boolean onlyAAA) {
        this.onlyAAA = onlyAAA;
    }

    public Boolean getOnlySale() {
        return onlySale;
    }

    public void setOnlySale(Boolean onlySale) {
        this.onlySale = onlySale;
    }

    public int getMetacriticRating() {
        return MetacriticRating;
    }

    public void setMetacriticRating(int minMetacriticRating) {
        this.MetacriticRating = minMetacriticRating;
    }

    public int getSteamRating() {
        return SteamRating;
    }

    public void setSteamRating(int minSteamRating) {
        this.SteamRating = minSteamRating;
    }
}
