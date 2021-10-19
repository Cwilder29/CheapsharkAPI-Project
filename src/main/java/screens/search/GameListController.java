package screens.search;

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
import screens.MyController;
import screens.ScreenType;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameListController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private ListView<Game> gameList;

    private String gameTitle;
    private ArrayList<Game> games;
    private int statusCode;

    public GameListController(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void getGameDeals() {
        this.games = new ArrayList<>();
        String url = "https://www.cheapshark.com/api/1.0/games?title=" + this.gameTitle; //TODO check for spaces

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(getRequest);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200)
                LOGGER.info("Games successfully retrieved from CheapShark: " + statusCode);
            else
            {
                LOGGER.error("Could not retrieve games from CheapShark: " + statusCode);
                return;
            }

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            JSONArray objResponse = new JSONArray(strResponse);

            for (Object game : objResponse) {
                games.add(Game.fromJSONObject((JSONObject) game));
            }

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickGame(MouseEvent event) {
        Game selectedGame;
        if (event.getClickCount() == 2) {
            selectedGame = gameList.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                LOGGER.info("Loading information on <" + selectedGame + ">");
                MainController.getInstance().switchView(ScreenType.VIEW_GAME, selectedGame, gameTitle);
            }
        }
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.MAINMENU);
    }

    @FXML
    void newSearch(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.GAME_PARAMETERS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameTitle = gameTitle.replaceAll("\\s+","");

        getGameDeals();

        if (statusCode == 200) {
            ObservableList<Game> tempList = FXCollections.observableArrayList(games);
            // plug the observable array list into the list
            gameList.setItems(tempList);
        }
        else {
            Alerts.infoAlert("Error!", "Could not retrieve games from CheapShark: " + statusCode);
        }
    }
}
