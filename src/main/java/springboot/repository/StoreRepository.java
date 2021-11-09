package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.model.Store;

import java.util.Optional;

public interface StoreRepository extends JpaRepository <Store, Integer> {
}
