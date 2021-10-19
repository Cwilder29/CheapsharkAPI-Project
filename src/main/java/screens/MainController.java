package screens;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import model.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import screens.screenfiles.*;
import screens.screentypes.*;
import screens.search.GameListController;
import screens.search.GameParametersController;
import screens.search.GameViewController;

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

    // TODO Change from passing in screenType to ScreenFile implemented class object !!!!
    public void switchView(ScreenType screenType, Object... args) {
        String viewFileName = "";
        SelectedController controller = null;
        FXMLLoader loader = null; // TODO temp

        // New class/method that returns controller
        // TODO: move switchView switchcase into its own class or method. Create Screen object initialized with screentype
        //     make new method for each case statement.
        switch (screenType) {
            case MAIN_MENU:
                loader = new MainMenuScreen().getScreenController(new MainMenuFile().getScreenFile());
                break;
            case DEAL_PARAMETERS:
                loader = new DealParametersScreen().getScreenController(new DealParametersFile().getScreenFile());
                break;
            case GAME_PARAMETERS:
                loader = new GameParametersScreen().getScreenController(new GameParametersFile().getScreenFile());
                break;
            case DEAL_LIST:
                loader = new DealListScreen().getScreenController(new DealListFile().getScreenFile(), args);
                break;
            case DEAL_VIEW:
                loader = new DealViewScreen().getScreenController(new DealViewFile().getScreenFile(), args);
                break;
            case GAME_LIST:
                loader = new GameListScreen().getScreenController(new GameListFile().getScreenFile(), args);
                break;
            case GAME_VIEW:
                loader = new GameViewScreen().getScreenController(new GameViewFile().getScreenFile(), args);
                break;
        }
        //FXMLLoader loader = new FXMLLoader(this.getClass().getResource(viewFileName));
        //loader.setController(controller);
        Parent rootNode = null;
        try {
            rootNode = loader.load();
            rootPane.setCenter(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchView(ScreenType.MAIN_MENU);
    }

    public static MainController getInstance() {
        if(instance == null)
            instance = new MainController();
        return instance;
    }

    // accessors
}
