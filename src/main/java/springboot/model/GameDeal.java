package springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "deals")
public class GameDeal {
    @Id
    @Column(name = "deal_id", nullable = false, length = 100)
    private String dealId;

    @Column(name = "game_id", nullable = false)
    private int gameId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "sale_price", nullable = false)
    private float salePrice;

    @Column(name = "normal_price", nullable = false)
    private float normalPrice;

    @Column(name = "store_id", nullable = false)
    private int storeId;

    public GameDeal() {
    }

    @Override
    public String toString() {
        return "GameDeal{" +
                "dealId='" + dealId + '\'' +
                ", gameId=" + gameId +
                ", title='" + title + '\'' +
                ", salePrice=" + salePrice +
                ", normalPrice=" + normalPrice +
                ", storeId=" + storeId +
                '}';
    }

    // accessors
    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
