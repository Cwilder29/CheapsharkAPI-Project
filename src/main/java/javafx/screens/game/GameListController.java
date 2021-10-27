package javafx.screens.game;

import javafx.httpclient.GetRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.model.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.screens.MainController;
import javafx.screens.SelectedController;
import javafx.screens.screentypes.GameParametersScreen;
import javafx.screens.screentypes.GameViewScreen;
import javafx.screens.screentypes.MainMenuScreen;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameListController implements Initializable, SelectedController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private ListView<Game> gameList;

    private String gameTitle;
    private ArrayList<Game> games;

    public GameListController(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void getGameDeals() {
        this.games = new ArrayList<>();
        String url = "https://www.cheapshark.com/api/1.0/games?title=" + this.gameTitle;

        String strResponse = new GetRequest().executeRequest(url, "");

        if (strResponse != null) {
            JSONArray objResponse = new JSONArray(strResponse);

            for (Object game : objResponse) {
                games.add(Game.fromJSONObject((JSONObject) game));
            }
        }
    }

    @FXML
    void clickGame(MouseEvent event) {
        Game selectedGame;
        if (event.getClickCount() == 2) {
            selectedGame = gameList.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                LOGGER.info("Loading information on <" + selectedGame + ">");
                MainController.getInstance().switchView(new GameViewScreen().getScreenController(selectedGame, gameTitle));

            }
        }
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(new MainMenuScreen().getScreenController());
    }

    @FXML
    void newSearch(ActionEvent event) {
        MainController.getInstance().switchView(new GameParametersScreen().getScreenController());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameTitle = gameTitle.replaceAll("\\s+","");

        getGameDeals();

        ObservableList<Game> tempList = FXCollections.observableArrayList(games);
        // plug the observable array list into the list
        gameList.setItems(tempList);
    }
}
