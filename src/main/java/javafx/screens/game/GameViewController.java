package javafx.screens.game;

import javafx.httpclient.GetRequest;
import javafx.httpclient.PostRequest;
import javafx.scene.paint.Color;
import javafx.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.model.Game;
import javafx.model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.screens.MainController;
import javafx.screens.SelectedController;
import javafx.screens.RetrieveStores;
import javafx.screens.screentypes.GameListScreen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameViewController implements Initializable, SelectedController {
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
    @FXML
    private Label fxSave;

    private Game game;
    private ArrayList<Store> stores;
    private JSONObject gameDetails;
    private JSONArray gameDeals;
    private String dealId;
    private String previousSearch;
    private int statusCode;

    public GameViewController(Game game, String previousSearch) {
        this.game = game;
        this.previousSearch = previousSearch;
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(new GameListScreen().getScreenController(previousSearch));
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

    @FXML
    void saveGame(ActionEvent event) {
        GetRequest request = new GetRequest();
        String strResponse;
        String url = "https://www.cheapshark.com/api/1.0/deals?id=" + dealId;

        strResponse = request.executeRequest(url, "");

        JSONObject jsonResponse = new JSONObject(strResponse);

        JSONObject lookupData = new JSONObject(jsonResponse.getJSONObject("gameInfo").toString());

        LOGGER.info("Saving into database...");
        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        LOGGER.info("IP Address:- " + inetAddress.getHostAddress());
        String ip = inetAddress.getHostAddress();
        url = "http://" + ip + ":8080/deals";

        JSONObject dealData = new JSONObject();

        dealData.put("title", lookupData.getString("name"));
        dealData.put("dealID", dealId);
        dealData.put("salePrice", lookupData.getFloat("salePrice"));
        dealData.put("normalPrice", lookupData.getFloat("retailPrice"));
        dealData.put("gameID", game.getGameId());
        dealData.put("storeID", lookupData.getInt("storeID"));
        dealData.put("savings", game.getSavings());
        dealData.put("metacriticScore", lookupData.getInt("metacriticScore"));
        dealData.put("steamRatingPercent", lookupData.getInt("steamRatingPercent"));
        dealData.put("thumb", lookupData.getString("thumb"));
        String dealDataString = dealData.toString();

        strResponse = new PostRequest().executeRequest(url, dealDataString);

        if (strResponse != null) {
            fxSave.setText("Deal saved!");
            fxSave.setTextFill(Color.web("#38BC00"));
        }
        else {
            fxSave.setText("Did not save!");
            fxSave.setTextFill(Color.web("#FF0000"));
        }
    }

    public void viewGame (Game game) {
        String url = "https://www.cheapshark.com/api/1.0/games?id=" + game.getGameId();

        String strResponse = new GetRequest().executeRequest(url, "");

        if (strResponse != null)
            this.gameDetails = new JSONObject(strResponse);
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
                storeId = store.getId();
            }
        }

        for (Object dealSite : gameDeals) {
            if (((JSONObject) dealSite).getInt("storeID") == storeId) {
                LOGGER.info("Setting new price");
                fxPrice.setText(((JSONObject) dealSite).getString("price"));
                fxRetailPrice.setText(((JSONObject) dealSite).getString("retailPrice"));
                fxSavings.setText(checkSavings(((JSONObject) dealSite).getString("savings")));
                game.setSavings(((JSONObject) dealSite).getFloat("savings"));
                dealId = ((JSONObject) dealSite).getString("dealID");
                break;
            }
        }

    }

    private String checkSavings (String savings) {
        int savingsRounded;

        try {
            savingsRounded = (int) Double.parseDouble(savings);

            if (Double.parseDouble(savings) < 1) {
                return "Not on sale";
            }
            else
                return (int) Double.parseDouble(savings) + "%";
        } catch (NumberFormatException e) {
            LOGGER.error("Error parsing savings:" + e);
            return savings;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stores = RetrieveStores.retrieveStores();
        int storeId;

        viewGame(this.game);

        if (gameDetails != null) {
            JSONObject infoResponse = new JSONObject(gameDetails.getJSONObject("info").toString());
            this.fxTitle.setText(infoResponse.getString("title"));
            this.gameDeals = new JSONArray(gameDetails.getJSONArray("deals").toString());
            LOGGER.info(gameDeals.toString());

            for (Object dealSite : this.gameDeals) {
                storeId = getDealWebsite((JSONObject) dealSite);
                if (this.stores != null) {
                    for (Store store : this.stores) {
                        if (storeId == store.getId()) {
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
        else {
            Alerts.infoAlert("Error!", "Could not load game information from CheapShark: " + statusCode);
        }
    }
}
