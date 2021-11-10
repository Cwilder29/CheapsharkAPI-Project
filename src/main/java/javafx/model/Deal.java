package javafx.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Deal {

    private String title;
    private String dealId;
    private float salePrice;
    private float normalPrice;
    private double savings;
    private int metacriticRating;
    private int steamRating;
    private int storeId;
    private int gameId;
    private String imageLink;

    private int databaseId;

    private Store store;

    public Deal(String title, String dealId, float salePrice, float normalPrice, double savings,
                int metacriticRating, int steamRating, int storeId, int gameId, String imageLink) {
        this.title = title;
        this.dealId = dealId;
        this.salePrice = salePrice;
        this.normalPrice = normalPrice;
        this.savings = savings;
        this.metacriticRating = metacriticRating;
        this.steamRating = steamRating;
        this.storeId = storeId;
        this.gameId = gameId;
        this.imageLink = imageLink;
    }

    // Used for deal objects stored in database
    public Deal(String title, String dealId, float salePrice, float normalPrice, double savings,
                int metacriticRating, int steamRating, int gameId, String imageLink, int databaseId, Store store) {
        this.title = title;
        this.dealId = dealId;
        this.salePrice = salePrice;
        this.normalPrice = normalPrice;
        this.savings = savings;
        this.metacriticRating = metacriticRating;
        this.steamRating = steamRating;
        this.gameId = gameId;
        this.imageLink = imageLink;
        this.databaseId = databaseId;
        this.store = store;
    }

    public static Deal fromJSONObject(JSONObject json) {
        try {
            Deal deal = new Deal(json.getString("title"), json.getString("dealID"),
                                 json.getFloat("salePrice"), json.getFloat("normalPrice"),
                                 json.getDouble("savings"), json.getInt("metacriticScore"),
                                 json.getInt("steamRatingPercent"), json.getInt("storeID"),
                                 json.getInt("gameID"), json.getString("thumb"));
            return deal;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse gameDeal from provided json:\n " + json.toString());
        }
    }

    public static Deal fromJSONObjectDatabase(JSONObject json) {
        JSONObject storeJSON = new JSONObject(json.getJSONObject("store").toString());

        try {
            Store store = Store.fromJSONObjectDatabase(storeJSON);

            Deal deal = new Deal(json.getString("title"), json.getString("dealID"),
                    json.getFloat("salePrice"), json.getFloat("normalPrice"),
                    json.getDouble("savings"), json.getInt("metacriticScore"),
                    json.getInt("steamRatingPercent"), json.getInt("gameID"), json.getString("thumb"),
                    json.getInt("id"), store);
            return deal;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse gameDeal from provided json:\n " + json.toString());
        }
    }

    @Override
    public String toString() {
        return getTitle(); // TODO Find out how to get store name
    }

    // accessors
    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public float getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(float normalPrice) {
        this.normalPrice = normalPrice;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public int getMetacriticRating() {
        return metacriticRating;
    }

    public void setMetacriticRating(int metacriticRating) {
        this.metacriticRating = metacriticRating;
    }

    public int getSteamRating() {
        return steamRating;
    }

    public void setSteamRating(int steamRating) {
        this.steamRating = steamRating;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
