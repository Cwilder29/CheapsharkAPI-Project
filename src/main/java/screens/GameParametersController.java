package screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class GameParametersController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private TextField titleSearch;

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.MAINMENU);
    }

    @FXML
    void search(ActionEvent event) {
        LOGGER.info(this.titleSearch.getText());
        MainController.getInstance().switchView(ScreenType.GAME_LIST, titleSearch.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
