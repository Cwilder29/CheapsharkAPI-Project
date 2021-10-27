package springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "deals")
public class GameDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "deal_id", nullable = false, length = 100)
    private String dealID;

    @Column(name = "game_id", nullable = false)
    private int gameID;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "sale_price", nullable = false)
    private float salePrice;

    @Column(name = "normal_price", nullable = false)
    private float normalPrice;

    @Column(name = "store_id", nullable = false)
    private int storeID;

    @Column(name = "savings", nullable = true)
    private float savings;

    @Column(name = "metacritic_score", nullable = true)
    private int metacriticScore;

    @Column(name = "steam_rating_percent", nullable = true)
    private int steamRatingPercent;

    @Column(name = "thumb", nullable = true)
    private String thumb;

    public GameDeal() {
    }

    @Override
    public String toString() {
        return "GameDeal{" +
                "id=" + id +
                ", dealID='" + dealID + '\'' +
                ", gameID=" + gameID +
                ", title='" + title + '\'' +
                ", salePrice=" + salePrice +
                ", normalPrice=" + normalPrice +
                ", storeID=" + storeID +
                ", savings=" + savings +
                ", metacriticScore=" + metacriticScore +
                ", steamRatingPercent=" + steamRatingPercent +
                ", thumb='" + thumb + '\'' +
                '}';
    }

    // accessors
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDealID() {
        return dealID;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

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

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public float getSavings() {
        return savings;
    }

    public void setSavings(float savings) {
        this.savings = savings;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public int getSteamRatingPercent() {
        return steamRatingPercent;
    }

    public void setSteamRatingPercent(int steamRatingPercent) {
        this.steamRatingPercent = steamRatingPercent;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
