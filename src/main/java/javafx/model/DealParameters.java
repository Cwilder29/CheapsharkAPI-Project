package javafx.model;

public class DealParameters {
    // Implemented
    private Store store;
    private int upperPrice;
    private Sort sortBy;
    private Boolean onlyAAA;
    private Boolean onlySale;
    private int MetacriticRating;
    private int SteamRating;
    private int pageNumber;

    // Not Implemented
    private int pageSize;
    private int lowerPrice;
    private boolean sortDirection;
    private String title;
    private boolean exactSearch;
    private boolean steamWorks;

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

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(int lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public boolean isSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(boolean sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExactSearch() {
        return exactSearch;
    }

    public void setExactSearch(boolean exactSearch) {
        this.exactSearch = exactSearch;
    }

    public boolean isSteamWorks() {
        return steamWorks;
    }

    public void setSteamWorks(boolean steamWorks) {
        this.steamWorks = steamWorks;
    }
}
