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

        switch (screenType) {
            case GAMELIST:
                ArrayList<GameDeal> games = new ArrayList<>();
                viewFileName = "/list_games.fxml";
                games = GameListController.getGameDeals();
                controller = new GameListController(games);
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
        switchView(ScreenType.GAMELIST);
    }

    public static MainController getInstance() {
        if(instance == null)
            instance = new MainController();
        return instance;
    }

    // accessors
}
