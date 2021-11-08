package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.model.Store;

public interface StoreRepository extends JpaRepository <Store, Integer> {
}
