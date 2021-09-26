import org.json.JSONObject;

public class GameDeal {

    private String title;
    private float salePrice;
    private float normalPrice;
    private double savings;
    private int metacriticRating;
    private int steamRating;

    public GameDeal(String title, float salePrice, float normalPrice, double savings, int metacriticRating, int steamRating) {
        this.title = title;
        this.salePrice = salePrice;
        this.normalPrice = normalPrice;
        this.savings = savings;
        this.metacriticRating = metacriticRating;
        this.steamRating = steamRating;
    }

    public static GameDeal fromJSONObject(JSONObject json) {
        try {
            GameDeal gameDeal = new GameDeal(json.getString("title"), json.getFloat("salePrice"), json.getFloat("normalPrice"),
                     json.getDouble("savings"), json.getInt("metacriticScore"), json.getInt("steamRatingPercent"));
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
