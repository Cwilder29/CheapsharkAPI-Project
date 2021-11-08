package springboot.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springboot.model.Store;
import springboot.repository.StoreRepository;

import java.util.ArrayList;

@RestController
public class StoreController {
    private static final Logger LOGGER = LogManager.getLogger();

    private StoreRepository storeRepository;

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @PostMapping("/stores")
    public ResponseEntity<?> insertStores(@RequestBody ArrayList<Store> stores) {
        storeRepository.saveAll(stores);

        return new ResponseEntity<>("", HttpStatus.valueOf(200));
    }
}
