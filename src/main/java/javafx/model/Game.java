package javafx.model;

import org.json.JSONObject;

public class Game {
    private int gameId;
    private int steamAppId;
    private float cheapestPrice;
    private float savings;

    private String cheapestDealId;
    private String gameTitle;

    public Game(int gameId, float cheapestPrice, String cheapestDealId, String gameTitle) {
        this.gameId = gameId;
        this.cheapestPrice = cheapestPrice;
        this.cheapestDealId = cheapestDealId;
        this.gameTitle = gameTitle;
    }

    public static Game fromJSONObject(JSONObject json) {
        try {
            Game game = new Game(json.getInt("gameID"), json.getFloat("cheapest"),
                    json.getString("cheapestDealID"), json.getString("external"));
            return game;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse game from provided json:\n" + e + "\n " + json.toString());
        }
    }

    @Override
    public String toString() {
        return gameTitle;
    }

    //accessors
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getSteamAppId() {
        return steamAppId;
    }

    public void setSteamAppId(int steamAppId) {
        this.steamAppId = steamAppId;
    }

    public float getCheapestPrice() {
        return cheapestPrice;
    }

    public void setCheapestPrice(float cheapestPrice) {
        this.cheapestPrice = cheapestPrice;
    }

    public String getCheapestDealId() {
        return cheapestDealId;
    }

    public void setCheapestDealId(String cheapestDealId) {
        this.cheapestDealId = cheapestDealId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public float getSavings() {
        return savings;
    }

    public void setSavings(float savings) {
        this.savings = savings;
    }
}
