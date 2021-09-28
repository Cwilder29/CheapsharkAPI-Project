package model;

public enum Store {
    STEAM (1, "Steam"), GREENMANGAMING (3, "Green Man Gaming"), AMAZON (4, "Amazon"),
    GAMESTOP (5, "GameStop"), GOG (7, "GOG"), ORIGIN (8, "Origin"),
    HUMBLESTORE (11, "Humble model.Store"), UPLAY (13, "Uplay");

    private final int storeId;
    private final String storeName;

    Store(int storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return storeName;
    }

    // accessors
    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }
}
