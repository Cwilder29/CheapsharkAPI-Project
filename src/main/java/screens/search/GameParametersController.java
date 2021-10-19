package screens.search;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import screens.MainController;
import screens.SelectedController;
import screens.screentypes.ScreenType;

import java.net.URL;
import java.util.ResourceBundle;

public class GameParametersController implements Initializable, SelectedController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private TextField titleSearch;

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.MAIN_MENU);
    }

    @FXML
    void search(ActionEvent event) {
        LOGGER.info(titleSearch.getText());
        MainController.getInstance().switchView(ScreenType.GAME_LIST, titleSearch.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
