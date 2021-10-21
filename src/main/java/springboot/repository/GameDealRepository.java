package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.model.GameDeal;

public interface GameDealRepository extends JpaRepository <GameDeal, String>{
}
