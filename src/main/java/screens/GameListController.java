package screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.GameDeal;
import model.Store;
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

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameListController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Store selectedStore;

    @FXML
    private ListView<GameDeal> gameList;

    public GameListController(Store selectedStore) {
        this.selectedStore = selectedStore;
    }

    @FXML
    void clickGame(MouseEvent event) {
        GameDeal selectedGame;
        if (event.getClickCount() == 2) {
            selectedGame = gameList.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                LOGGER.info("Loading information on <" + selectedGame.getTitle() + ">");
                MainController.getInstance().switchView(ScreenType.GAMEVIEW, selectedGame, selectedStore);
            }
        }
    }

    public static ArrayList<GameDeal> getGameDeals(Store store) {
        int statusCode;
        ArrayList<GameDeal> games = new ArrayList<>();
        String url = "https://www.cheapshark.com/api/1.0/deals?";
        String storeId = "storeID=" + store.getStoreId();
        String upperPrice = "&upperPrice=15";

        LOGGER.info("Selected store:" + store.getStoreName() + " (id:" + store.getStoreId() + ")");

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet(url + storeId + upperPrice);
            CloseableHttpResponse response = httpclient.execute(getRequest);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200)
                LOGGER.info("Game deals successfully retrieved from CheapShark: " + statusCode);
            else
                LOGGER.error("Could not retrieve game deals from CheapShark: " + statusCode);

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            JSONArray objResponse = new JSONArray(strResponse);

            for (Object game : objResponse) {
                games.add(GameDeal.fromJSONObject((JSONObject) game));
            }

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (model.GameDeal game : gameList) {
//            System.out.println(game.toString());
//        }
        return games;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. turn plain ol arraylist of models into an ObservableArrayList
        ArrayList<GameDeal> games = getGameDeals(selectedStore);
        ObservableList<GameDeal> tempList = FXCollections.observableArrayList(games);

        // 2. plug the observable array list into the list
        gameList.setItems(tempList);
    }
}
