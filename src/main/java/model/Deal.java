package model;

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

    public Deal(String title, String dealId, float salePrice, float normalPrice, double savings,
                int metacriticRating, int steamRating, int storeId, int gameId) {
        this.title = title;
        this.dealId = dealId;
        this.salePrice = salePrice;
        this.normalPrice = normalPrice;
        this.savings = savings;
        this.metacriticRating = metacriticRating;
        this.steamRating = steamRating;
        this.storeId = storeId;
        this.gameId = gameId;
    }

    public static Deal fromJSONObject(JSONObject json) {
        try {
            Deal deal = new Deal(json.getString("title"), json.getString("dealID"),
                                 json.getFloat("salePrice"), json.getFloat("normalPrice"),
                                 json.getDouble("savings"), json.getInt("metacriticScore"),
                                 json.getInt("steamRatingPercent"), json.getInt("storeID"),
                                 json.getInt("gameID"));
            return deal;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse gameDeal from provided json:\n " + json.toString());
        }
    }

    public static Deal fromDatabaseJSONObject(JSONObject json) {
        try {
            Deal deal = new Deal(json.getString("title"), json.getString("dealId"),
                    json.getFloat("salePrice"), json.getFloat("normalPrice"),
                    json.getDouble("savings"), json.getInt("metacriticScore"),
                    json.getInt("steamRatingPercent"), json.getInt("storeID"),
                    json.getInt("gameID"));
            return deal;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse gameDeal from provided json:\n " + json.toString());
        }
    }

    @Override
    public String toString() {
        return getTitle() + " (Store name)"; // TODO Find out how to get store name
    }

    // accessors
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
}
