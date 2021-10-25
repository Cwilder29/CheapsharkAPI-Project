package screens;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import screens.screentypes.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static MainController instance = null;

    @FXML
    private BorderPane rootPane;

    private MainController() {
    }

    public void switchView(FXMLLoader loader) {
        Parent rootNode = null;
        try {
            rootNode = loader.load();
            rootPane.setCenter(rootNode);
        } catch (IOException e) {
            LOGGER.error(e.getCause());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchView(new MainMenuScreen().getScreenController());
    }

    public static MainController getInstance() {
        if(instance == null)
            instance = new MainController();
        return instance;
    }
}
