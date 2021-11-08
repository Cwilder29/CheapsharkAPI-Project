package springboot.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.model.Store;
import springboot.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StoreController {
    private static final Logger LOGGER = LogManager.getLogger();

    private StoreRepository storeRepository;

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> fetchStores() {
        List<Store> stores = storeRepository.findAll();

        return new ResponseEntity<>(stores, HttpStatus.valueOf(200));
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<?> fetchStoreById(@PathVariable int id) {
        Optional<Store> store = storeRepository.findById(id);

        if (store.isPresent())
            return new ResponseEntity<>(store.get(), HttpStatus.valueOf(200));
        else
            return new ResponseEntity<>("", HttpStatus.valueOf(404));
    }

    @PostMapping("/stores")
    public ResponseEntity<?> insertStores(@RequestBody ArrayList<Store> stores) {
        LOGGER.info(stores);
        storeRepository.saveAll(stores);

        return new ResponseEntity<>("", HttpStatus.valueOf(200));
    }
}
