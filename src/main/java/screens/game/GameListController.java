package screens.game;

import httpclient.GetRequest;
import javafx.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.Game;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import screens.MainController;
import screens.SelectedController;
import screens.screentypes.GameParametersScreen;
import screens.screentypes.GameViewScreen;
import screens.screentypes.MainMenuScreen;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

        String strResponse = new GetRequest().executeRequest(url);

        JSONArray objResponse = new JSONArray(strResponse);

        for (Object game : objResponse) {
            games.add(Game.fromJSONObject((JSONObject) game));
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
