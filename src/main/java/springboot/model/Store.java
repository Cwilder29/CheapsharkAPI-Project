package springboot.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stores")
public class Store {
    @Id
    @Column
    private int id;

    @Column(name = "store_name", nullable = false, length = 100)
    private String storeName;

    @Column(name = "active", nullable = false)
    private int storeActive;

    @OneToMany(mappedBy = "store")
    private Set<GameDeal> gameDeals;

    public Store() {
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", storeActive=" + storeActive +
                '}';
    }

    //accessors
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreActive() {
        return storeActive;
    }

    public void setStoreActive(int storeActive) {
        this.storeActive = storeActive;
    }
}
