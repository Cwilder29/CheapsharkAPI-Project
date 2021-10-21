package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.model.GameDeal;

import java.util.List;
import java.util.Optional;

public interface GameDealRepository extends JpaRepository <GameDeal, Integer>{
    Optional<GameDeal> findGameDealByDealId(String dealId);
    Optional<List<GameDeal>> findAllByIdNot(Integer id);
}
