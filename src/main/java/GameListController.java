import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
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

    private ArrayList<GameDeal> games;

    @FXML
    private ListView<GameDeal> gameList;

    public GameListController(ArrayList<GameDeal> gameList) {
        this.games = gameList;
    }

    public static ArrayList<GameDeal> getGameDeals() {
        ArrayList<GameDeal> games = new ArrayList<>();

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet("https://www.cheapshark.com/api/1.0/deals?storeID=1&upperPrice=15");
            CloseableHttpResponse response = httpclient.execute(getRequest);

            System.out.println(response.getStatusLine());

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            System.out.println(strResponse);

            JSONArray objResponse = new JSONArray(strResponse);

            for (Object game : objResponse) {
                games.add(GameDeal.fromJSONObject((JSONObject) game));
            }

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (GameDeal game : gameList) {
//            System.out.println(game.toString());
//        }
        return games;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. turn plain ol arraylist of models into an ObservableArrayList
        ObservableList<GameDeal> tempList = FXCollections.observableArrayList(games);

        // 2. plug the observable array list into the list
        gameList.setItems(tempList);
        getGameDeals();
    }
}
