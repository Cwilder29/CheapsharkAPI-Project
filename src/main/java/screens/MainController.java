package screens;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import model.DealParameters;
import model.Game;
import model.Deal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import screens.list.DealListController;
import screens.list.DealParametersController;
import screens.list.DealViewController;
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

    public void switchView(ScreenType screenType, Object... args) {
        String viewFileName = "";
        MyController controller = null;

        switch (screenType) {
            case MAINMENU:
                viewFileName = "/menu.fxml";
                controller = new MenuController();
                break;
            case DEAL_PARAMETERS:
                viewFileName = "/deal_parameters.fxml";
                controller = new DealParametersController();
                break;
            case GAME_PARAMETERS:
                viewFileName = "/game_parameters.fxml";
                controller = new GameParametersController();
                break;
            case DEAL_LIST:
                viewFileName = "/list_deals.fxml";
                if(!(args[0] instanceof DealParameters)) {
                    throw new IllegalArgumentException("Invalid model. model.Store object! " + args[0].toString());
                }
                controller = new DealListController((DealParameters) args[0]);
                break;
            case DEAL_VIEW:
                viewFileName = "/view_deal.fxml";
                if(!(args[0] instanceof Deal)) {
                    throw new IllegalArgumentException("Invalid model. GameDeal object!" + args[0].toString());
                }
                controller = new DealViewController((Deal) args[0], (DealParameters) args[1]);
                break;
            case GAME_LIST:
                viewFileName = "/list_games.fxml";
                if(!(args[0] instanceof String)) {
                    throw new IllegalArgumentException("Invalid model. String!" + args[0].toString());
                }
                controller = new GameListController((String) args[0]);
                break;
            case VIEW_GAME:
                viewFileName = "/view_game.fxml";
                if(!(args[0] instanceof Game)) {
                    throw new IllegalArgumentException("Invalid model. String!" + args[0].toString());
                }
                controller = new GameViewController((Game) args[0]);
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
