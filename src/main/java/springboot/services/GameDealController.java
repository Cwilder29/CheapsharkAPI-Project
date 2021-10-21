package springboot.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.model.GameDeal;
import springboot.repository.GameDealRepository;

import java.util.List;

@RestController
public class GameDealController {
    private static final Logger LOGGER = LogManager.getLogger();

    private GameDealRepository gameDealRepository;

    public GameDealController(GameDealRepository gameDealRepository) {
        this.gameDealRepository = gameDealRepository;
    }

    @GetMapping("/games")
    public ResponseEntity<?> fetchGames() {
        List<GameDeal> games = gameDealRepository.findAll();
        LOGGER.info(games.toString());

        return new ResponseEntity<>(games, HttpStatus.valueOf(200));
    }
}
