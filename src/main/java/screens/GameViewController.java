package screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Game;
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

public class GameViewController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private Label fxTitle;
    @FXML
    private ChoiceBox<String> fxStore;
    @FXML
    private TextField fxPrice;
    @FXML
    private TextField fxRetailPrice;
    @FXML
    private TextField fxSavings;

    private Game game;
    private JSONObject gameDetails;

    public GameViewController(Game game) {
        this.game = game;
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.MAINMENU); //todo change to go back to game list controller
    }

    @FXML
    void viewDeal(ActionEvent event) {
        LOGGER.info("Opening store website in default browser...");
    }

    public void viewGame (Game game) {
        int statusCode;
        String url = "https://www.cheapshark.com/api/1.0/games?id=" + game.getGameId();
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(getRequest);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200)
                LOGGER.info("Games successfully retrieved from CheapShark: " + statusCode);
            else {
                LOGGER.error("Could not retrieve games from CheapShark: " + statusCode);
                response.close();
                httpclient.close();
                return;
            }
            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            //LOGGER.info(strResponse);

            //JSONObject objResponse = new JSONObject(strResponse);
            this.gameDetails = new JSONObject(strResponse);
            //JSONObject infoResponse = new JSONObject(objResponse.getJSONObject("info").toString());
            //JSONArray dealResponse = new JSONArray(objResponse.getJSONArray("deals"));

            //LOGGER.info(objResponse.toString());
            //LOGGER.info(objResponse.get("info"));
            //LOGGER.info(objResponse.getJSONObject("info").toString());
            //LOGGER.info(infoResponse.getString("title"));

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getDealWebsite (JSONObject json) {
        int storeId;
        try {
            return storeId = json.getInt("storeID");
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Unable to parse gameDeal from provided json:\n " + json.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JSONArray gameDeals;
        ArrayList<Store> stores = RetrieveStores.retrieveStores();
        int storeId;

        viewGame(this.game);
        JSONObject infoResponse = new JSONObject(gameDetails.getJSONObject("info").toString());
        this.fxTitle.setText(infoResponse.getString("title"));
        gameDeals = new JSONArray(gameDetails.getJSONArray("deals").toString());

        for (Object dealSite : gameDeals) {
            storeId = getDealWebsite((JSONObject) dealSite);
            if (stores != null) {
                for (Store store : stores) {
                    if (storeId == store.getStoreId()) {
                        fxStore.getItems().add(store.getStoreName());
                        break;
                    }
                }
            }
            else
                LOGGER.error("No deals available");
        }

        fxStore.getSelectionModel().selectFirst();
    }
}
