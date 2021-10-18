package screens.list;

import javafx.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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
    @FXML
    private TextField metacriticRating;
    @FXML
    private TextField steamRating;
    @FXML
    private CheckBox onlyAAA;


    private ArrayList<Store> stores = new ArrayList<>();
    private DealParameters dealParameters = new DealParameters();

    public DealParametersController() {
    }

    @FXML
    void loadDeals(ActionEvent event) {
        String storeName = storeSelection.getSelectionModel().getSelectedItem();
        String sortType = sortSelection.getSelectionModel().getSelectedItem();

        // Check if no store is selected
        if (storeSelection.getSelectionModel().getSelectedItem() == null) {
            LOGGER.error("No store selected!");
            Alerts.infoAlert("No store selected!", "Please select a store to pull deals from.");
            return;
        }

        // Get user max price
        if (setUpperPrice() != 0)
            return;

        // Get user sort
        setSort(sortType);

        // Set metacritic
        if (setRating(metacriticRating, "Metacritic") == -1)
            return;

        // Set steam
        if (setRating(steamRating, "Steam") == -1)
            return;

        // Set AAA Flag
        setAAA();

        // Load user parameters for the selected store out of all possible stores
        for (Store selectedStore : stores) {
            if (storeName.equals(selectedStore.getStoreName())) {
                dealParameters.setStore(selectedStore);
                LOGGER.info("Loading deals from " + selectedStore.getStoreName() + "...");
                MainController.getInstance().switchView(ScreenType.DEAL_LIST, dealParameters);
            }
        }
    }

    private int setUpperPrice() {
        try {
            dealParameters.setUpperPrice(Integer.parseInt(priceSelection.getText()));
            if (dealParameters.getUpperPrice() <= 0) {
                LOGGER.error("upperPrice is less than 0");
                Alerts.infoAlert("Invalid Max Price!", "Please select a price range greater than 0.");
                return -1;
            }
            else {
                LOGGER.info("upperPrice set at " + dealParameters.getUpperPrice());
                return 0;
            }
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            Alerts.infoAlert("Invalid Max Price!", "Invalid response for Max Price");
            return 1;
        }
    }

    private void setSort(String sortType) {
        for (Sort sort : Sort.values()) {
            if (sort.getSortName().equals(sortType)) {
                dealParameters.setSortBy(sort);
                LOGGER.info("Sorting by: " + sort.getSortName());
                break;
            }
        }
    }

    private int setRating(TextField ratingTextField, String ratingName) {
        String minimumScore = ratingTextField.getText().replaceAll("\\s+","");

        if (minimumScore.isEmpty()) {
            LOGGER.info("No input for " + ratingName);
            return 0;
        }

        // Validate user input before setting value
        if (checkValidRating(minimumScore, ratingName)) {
            switch (ratingName) {
                case "Metacritic":
                    dealParameters.setMetacriticRating(Integer.parseInt(minimumScore));
                    break;
                case "Steam":
                    dealParameters.setSteamRating(Integer.parseInt(minimumScore));
                    break;
            }
            return 1;
        }
        else
            return -1;
    }

    private boolean checkValidRating(String rating, String ratingType) {
        int numRating;

        try {
            numRating = Integer.parseInt(rating);
            if (numRating >= 0 && numRating <= 100) {
                LOGGER.info(ratingType + " set to: " + numRating);
                return true;
            }
            else {
                LOGGER.error(ratingType + " rating must be in range from 0-100: " + rating);
                Alerts.infoAlert(ratingType + " rating is invalid ", ratingType + " rating must be in range from 0-100!");
                return false;
            }
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            Alerts.infoAlert(ratingType + " rating is invalid ", ratingType + " rating is not in the correct format!");
            return false;
        }
    }

    private void setAAA() {
        if (onlyAAA.isSelected()) {
            LOGGER.info("Search for only AAA is selected");
            dealParameters.setOnlyAAA(true);
        }
        else {
            LOGGER.info("Search for only AAA is NOT selected");
            dealParameters.setOnlyAAA(false);
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
