package javafx.model;

import org.json.JSONObject;

public class Store {
    private int storeId;
    private String storeName;
    private int storeActive;

    public Store(int storeId, String storeName, int storeActive) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeActive = storeActive;
    }

    public static Store fromJSONObject(JSONObject json) {
        try {
            Store store = new Store(json.getInt("storeID"), json.getString("storeName"), json.getInt("isActive"));
            return store;
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Unable to parse gameDeal from provided json:\n " + json.toString());
        }
    }


    @Override
    public String toString() {
        return storeName;
    }

    //accessors
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
