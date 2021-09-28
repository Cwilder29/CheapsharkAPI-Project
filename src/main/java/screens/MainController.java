package screens;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import model.GameDeal;
import model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger();

    private static MainController instance = null;

    @FXML
    private BorderPane rootPane;

    private MainController() {
    }

    public void switchView(ScreenType screenType, Object... args) {
        String viewFileName = "";
        MyController controller = null;

        switch (screenType) {
            case MAINMENU:
                viewFileName = "/main_menu.fxml";
                controller = new MainMenuController();
                break;
            case GAMELIST:
                viewFileName = "/list_games.fxml";
                if(!(args[0] instanceof Store)) {
                    throw new IllegalArgumentException("Invalid model.Store object!" + args[0].toString());
                }
                controller = new GameListController((Store) args[0]);
                break;
            case GAMEVIEW:
                viewFileName = "/view_game.fxml";
                if(!(args[0] instanceof GameDeal)) {
                    throw new IllegalArgumentException("Invalid model.GameDeal object!" + args[0].toString());
                }
                controller = new GameViewController((GameDeal) args[0], (Store) args[1]);
                break;
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(viewFileName));
        loader.setController(controller);
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
        switchView(ScreenType.MAINMENU);
    }

    public static MainController getInstance() {
        if(instance == null)
            instance = new MainController();
        return instance;
    }

    // accessors
}
