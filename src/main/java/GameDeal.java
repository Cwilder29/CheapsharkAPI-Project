import org.json.JSONObject;

public class GameDeal {

    private String title;
    private int gameID;
    private float salePrice;
    private float normalPrice;
    private int steamAppID;

    public GameDeal(String title, int gameID, float salePrice, float normalPrice, int steamAppID) {
        this.title = title;
        this.gameID = gameID;
        this.salePrice = salePrice;
        this.normalPrice = normalPrice;
        this.steamAppID = steamAppID;
    }

    public GameDeal(String title) {
        this.title = title;
    }

    public static GameDeal fromJSONObject(JSONObject json) {
        try {
            GameDeal gameDeal = new GameDeal(json.getString("title"));
            return gameDeal;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse gameDeal from provided json:\n " + json.toString());
        }
    }

    @Override
    public String toString() {
        return getTitle();
    }

    // accessors
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
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

    public int getSteamAppID() {
        return steamAppID;
    }

    public void setSteamAppID(int steamAppID) {
        this.steamAppID = steamAppID;
    }
}
