package springboot.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.model.GameDeal;
import springboot.repository.GameDealRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class GameDealController {
    private static final Logger LOGGER = LogManager.getLogger();

    private GameDealRepository gameDealRepository;

    public GameDealController(GameDealRepository gameDealRepository) {
        this.gameDealRepository = gameDealRepository;
    }

    @GetMapping("/deals")
    public ResponseEntity<List<GameDeal>> fetchDeals() {
        List<GameDeal> deals = gameDealRepository.findAll();
        LOGGER.info(deals.toString());

        return new ResponseEntity<>(deals, HttpStatus.valueOf(200));
    }

    @GetMapping("/deals/{id}")
    public ResponseEntity<?> fetchDealById(@PathVariable int id) {
        Optional<GameDeal> deal = gameDealRepository.findById(id);
        if (deal.isPresent())
            return new ResponseEntity<>(deal.get(), HttpStatus.valueOf(200));
        else
            return new ResponseEntity<>("", HttpStatus.valueOf(404));
    }

    @PostMapping("/deals")
    public ResponseEntity<?> insertDeal(@RequestBody GameDeal newDeal) {
        Optional<GameDeal> existingDeal;

        LOGGER.info("Checking for existing deal id: " + newDeal.getDealID());
        existingDeal = gameDealRepository.findGameDealByDealID(newDeal.getDealID());

        if (existingDeal.isEmpty()) {
            GameDeal savedDeal = gameDealRepository.save(newDeal);
            return new ResponseEntity<>(savedDeal.getId(), HttpStatus.valueOf(200));
        }
        else {
            LOGGER.error("Deal already exists in the database: " + existingDeal.get().getDealID());
            return new ResponseEntity<>("already exists", HttpStatus.valueOf(400));
        }
    }

    @PutMapping("/deals/{id}")
    public ResponseEntity<String> updateDeal(@PathVariable int id, @RequestBody GameDeal newDeal) {
        Optional<GameDeal> deal = gameDealRepository.findById(id);
        Optional<List<GameDeal>> otherDeals = gameDealRepository.findAllByIdNot(id);

        LOGGER.info(newDeal);

        // Check that the updated deal does not conflict with another deal id besides one being updated.
        if (otherDeals.isPresent()) {
            for (GameDeal existingDeal : otherDeals.get()) {
                if (newDeal.getDealID().equals(existingDeal.getDealID())) {
                    LOGGER.error("Updated deal id conflicts another existing deal!");
                    return new ResponseEntity<>("already exists", HttpStatus.valueOf(400));
                }
            }
        }

        if (deal.isEmpty())
            return new ResponseEntity<>("", HttpStatus.valueOf(404));
        else {
            newDeal.setId(id);
            gameDealRepository.save(newDeal);
            return new ResponseEntity<>("", HttpStatus.valueOf(200));
        }
    }

    @DeleteMapping("/deals/{id}")
    public ResponseEntity<String> deleteDeal(@PathVariable int id) {
        Optional<GameDeal> deal = gameDealRepository.findById(id);
        LOGGER.info(deal);

        if (deal.isPresent()) {
            gameDealRepository.deleteById(id);
            return new ResponseEntity<>("", HttpStatus.valueOf(200));
        } else
            return new ResponseEntity<>("", HttpStatus.valueOf(404));
    }
}