package screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private ChoiceBox<String> storeSelection;

    @FXML
    private Label storeLabel;

    private ArrayList<Store> stores = new ArrayList<>();

    public MainMenuController() {
    }

    @FXML
    void loadDeals(ActionEvent event) {
        String storeName = storeSelection.getSelectionModel().getSelectedItem();

        for (Store selectedStore : stores) {
            if (storeName.equals(selectedStore.getStoreName())) {
                LOGGER.info("Loading deals from " + selectedStore.getStoreName() + "...");
                MainController.getInstance().switchView(ScreenType.GAMELIST, selectedStore);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Store store : Store.values()) {
            stores.add(store);
            storeSelection.getItems().add(store.getStoreName());
        }
    }
}
