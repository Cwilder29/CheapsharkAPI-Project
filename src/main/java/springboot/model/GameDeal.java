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
    private String deal_id;

    @Column(name = "game_id", nullable = false)
    private int game_id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "sale_price", nullable = false)
    private float sale_price;

    @Column(name = "normal_price", nullable = false)
    private float normal_price;

    @Column(name = "store_id", nullable = false)
    private int store_id;

    @Column(name = "savings", nullable = true)
    private float savings;

    @Column(name = "metacritic_score", nullable = true)
    private int metacritic_score;

    @Column(name = "steam_rating_percent", nullable = true)
    private int steam_rating_percent;

    public GameDeal() {
    }

    @Override
    public String toString() {
        return "GameDeal{" +
                "id=" + id +
                ", deal_id='" + deal_id + '\'' +
                ", game_id=" + game_id +
                ", title='" + title + '\'' +
                ", sale_price=" + sale_price +
                ", normal_price=" + normal_price +
                ", store_id=" + store_id +
                ", savings=" + savings +
                ", metacritic_score=" + metacritic_score +
                ", steam_rating_percent=" + steam_rating_percent +
                '}';
    }

    // accessors
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public float getNormal_price() {
        return normal_price;
    }

    public void setNormal_price(float normal_price) {
        this.normal_price = normal_price;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public float getSavings() {
        return savings;
    }

    public void setSavings(float savings) {
        this.savings = savings;
    }

    public int getMetacritic_score() {
        return metacritic_score;
    }

    public void setMetacritic_score(int metacritic_score) {
        this.metacritic_score = metacritic_score;
    }

    public int getSteam_rating_percent() {
        return steam_rating_percent;
    }

    public void setSteam_rating_percent(int steam_rating_percent) {
        this.steam_rating_percent = steam_rating_percent;
    }
}
