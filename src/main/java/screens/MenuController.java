package screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable, MyController{
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    void listDeals(ActionEvent event) {
        LOGGER.info("Selected to view list of all game deals. Loading parameters...");
        MainController.getInstance().switchView(ScreenType.DEAL_PARAMETERS);
    }

    @FXML
    void searchGame(ActionEvent event) {
        LOGGER.info("Selected to search by game title. Loading parameters...");
        MainController.getInstance().switchView(ScreenType.GAME_PARAMETERS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
