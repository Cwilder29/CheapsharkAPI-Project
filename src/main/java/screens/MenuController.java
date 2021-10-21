package screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import screens.screentypes.DealParametersScreen;
import screens.screentypes.GameParametersScreen;
import screens.screentypes.SavedDealsScreen;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable, SelectedController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    void listDeals(ActionEvent event) {
        LOGGER.info("Selected to view list of all game deals. Loading parameters...");
        MainController.getInstance().switchView(new DealParametersScreen().getScreenController());
    }

    @FXML
    void searchGame(ActionEvent event) {
        LOGGER.info("Selected to search by game title. Loading parameters...");
        MainController.getInstance().switchView(new GameParametersScreen().getScreenController());
    }

    @FXML
    void viewSaved(ActionEvent event) {
        LOGGER.info("Viewing saved game deals. Loading...");
        MainController.getInstance().switchView(new SavedDealsScreen().getScreenController());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
