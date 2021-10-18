package screens.list;

import javafx.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.DealParameters;
import model.Sort;
import model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import screens.MainController;
import screens.MyController;
import screens.RetrieveStores;
import screens.ScreenType;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DealParametersController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private ChoiceBox<String> storeSelection;
    @FXML
    private TextField priceSelection;
    @FXML
    private ChoiceBox<String> sortSelection;

    private ArrayList<Store> stores = new ArrayList<>();
    private DealParameters dealParameters = new DealParameters();

    public DealParametersController() {
    }

    @FXML
    void loadDeals(ActionEvent event) {
        String storeName = storeSelection.getSelectionModel().getSelectedItem();
        String sortType = sortSelection.getSelectionModel().getSelectedItem();

        if (storeSelection.getSelectionModel().getSelectedItem() == null) {
            LOGGER.error("No store selected!");
            Alerts.infoAlert("No store selected!", "Please select a store to pull deals from.");
            return;
        }

        try {
            dealParameters.setUpperPrice(Integer.parseInt(this.priceSelection.getText()));
            if (dealParameters.getUpperPrice() <= 0) {
                LOGGER.error("upperPrice is less than 0");
                Alerts.infoAlert("Invalid Max Price!", "Please select a price range greater than 0.");
                return;
            }
            else
                LOGGER.info("upperPrice set at " + dealParameters.getUpperPrice());
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            Alerts.infoAlert("Invalid Max Price!", "Invalid response for Max Price");
            return;
        }

        for (Sort sort : Sort.values()) {
            if (sort.getSortName().equals(sortType)) {
                dealParameters.setSortBy(sort);
                LOGGER.info("Sorting by: " + sort.getSortName());
                break;
            }
        }

        for (Store selectedStore : stores) {
            if (storeName.equals(selectedStore.getStoreName())) {
                dealParameters.setStore(selectedStore);
                LOGGER.info("Loading deals from " + selectedStore.getStoreName() + "...");
                MainController.getInstance().switchView(ScreenType.DEAL_LIST, dealParameters);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stores = RetrieveStores.retrieveStores();

        for (Store store : stores) {
            storeSelection.getItems().add(store.getStoreName());
        }

        for (Sort sortType : Sort.values()) {
            sortSelection.getItems().add(sortType.getSortName());
        }
        storeSelection.getSelectionModel().selectFirst();
        sortSelection.getSelectionModel().selectFirst();
    }
}
