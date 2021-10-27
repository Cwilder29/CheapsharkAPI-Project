package javafx.model;

public enum Sort {
    DEAL_RATING("Deal Rating"), TITLE("Title"), SAVINGS("Savings"),
    PRICE("Price"), METACRITIC("Metacritic"), REVIEWS("Reviews"),
    RELEASE("Release"), RECENT("recent");

    private final String sortName;

    Sort(String sortName) {
        this.sortName = sortName;
    }

    @Override
    public String toString() {
        return sortName;
    }

    // accessors
    public String getSortName() {
        return sortName;
    }
}
