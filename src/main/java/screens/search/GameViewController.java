package screens.search;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
import screens.MainController;
import screens.MyController;
import screens.RetrieveStores;
import screens.ScreenType;

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
    private ComboBox<String> fxStore;
    @FXML
    private TextField fxPrice;
    @FXML
    private TextField fxRetailPrice;
    @FXML
    private TextField fxSavings;

    private Game game;
    private ArrayList<Store> stores;
    private JSONObject gameDetails;
    private JSONArray gameDeals;
    private String dealId;
    private String previousSearch;

    public GameViewController(Game game, String previousSearch) {
        this.game = game;
        this.previousSearch = previousSearch;
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.GAME_LIST, previousSearch);
    }

    @FXML
    void viewDeal(ActionEvent event) {
        LOGGER.info("Opening store website in default browser...");
        String url = "https://www.cheapshark.com/redirect?dealID=" + dealId;
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException e) {
            LOGGER.error("Could not loud website: " + e);
        }
    }

    @FXML
    void changeStore(ActionEvent event) {
        LOGGER.info("Loading new store info...");
        getGamePrices();
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

            this.gameDetails = new JSONObject(strResponse);

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getDealWebsite (JSONObject json) {
        try {
            return json.getInt("storeID");
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Unable to parse gameDeal from provided json:\n " + json.toString());
        }
    }

    private void getGamePrices() {
        String storeName = fxStore.getSelectionModel().getSelectedItem();
        int storeId = -1;
        LOGGER.info("New store selected:" + storeName);

        for (Store store : stores) {
            if (storeName.equals(store.getStoreName())) {
                storeId = store.getStoreId();
            }
        }

        for (Object dealSite : gameDeals) {
            if (((JSONObject) dealSite).getInt("storeID") == storeId) {
                LOGGER.info("Setting new price");
                fxPrice.setText(((JSONObject) dealSite).getString("price"));
                fxRetailPrice.setText(((JSONObject) dealSite).getString("retailPrice"));
                fxSavings.setText(((JSONObject) dealSite).getString("savings"));
                dealId = ((JSONObject) dealSite).getString("dealID");
                break;
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stores = RetrieveStores.retrieveStores();
        int storeId;

        viewGame(this.game);
        JSONObject infoResponse = new JSONObject(gameDetails.getJSONObject("info").toString());
        this.fxTitle.setText(infoResponse.getString("title"));
        this.gameDeals = new JSONArray(gameDetails.getJSONArray("deals").toString());
        LOGGER.info(gameDeals.toString());

        for (Object dealSite : this.gameDeals) {
            storeId = getDealWebsite((JSONObject) dealSite);
            if (this.stores != null) {
                for (Store store : this.stores) {
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
        getGamePrices();
    }
}
