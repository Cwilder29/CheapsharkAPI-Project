import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
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

        // TODO Find out a way to get a games steam ID
        // TODO Add option to save games temporality to an ArrayList (option to view it)
        // TODO Add a database to save a game's ID and to easily access later after app closes.
        // TODO Add images to application
        switch (screenType) {
            // TODO implement a main menu
            // TODO add screen to search for a specific game based off title and/or steam ID
            case MAINMENU:
                viewFileName = "/main_menu.fxml";
                controller = new MainMenuController();
                break;
            case GAMELIST:
                // TODO add store selection window before listing all game deals (current is Steam)
                // TODO add option to change upperPrice limit (prices less than or equal)
                // TODO add other options to configure
                ArrayList<GameDeal> games;
                viewFileName = "/list_games.fxml";
                if(!(args[0] instanceof Store)) {
                    throw new IllegalArgumentException("Invalid Store object!" + args[0].toString());
                }
                controller = new GameListController((Store) args[0]);
                break;
            case GAMEVIEW:
                // TODO add a redirect link to the cheapshark website of the selected game
                //      link would follow this format: https://www.cheapshark.com/redirect?dealID={id}
                viewFileName = "/view_game.fxml";
                if(!(args[0] instanceof GameDeal)) {
                    throw new IllegalArgumentException("Invalid GameDeal object!" + args[0].toString());
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
