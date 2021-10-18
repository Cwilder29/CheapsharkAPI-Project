package screens.list;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.DealParameters;
import model.GameDeal;
import model.Sort;
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
import screens.ScreenType;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DealListController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    private final DealParameters dealParameters;

    @FXML
    private ListView<GameDeal> gameList;


    public DealListController(DealParameters dealParameters) {
        this.dealParameters = dealParameters;
    }

    @FXML
    void clickGame(MouseEvent event) {
        GameDeal selectedGame;
        if (event.getClickCount() == 2) {
            selectedGame = gameList.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                LOGGER.info("Loading information on <" + selectedGame.getTitle() + ">");
                MainController.getInstance().switchView(ScreenType.DEAL_VIEW, selectedGame, dealParameters);
            }
        }
    }

    @FXML
    void newSearch(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.DEAL_PARAMETERS);
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.MAINMENU);
    }

    public static ArrayList<GameDeal> getGameDeals(DealParameters dealParameters) {
        int statusCode;
        ArrayList<GameDeal> games = new ArrayList<>();
        String url = "https://www.cheapshark.com/api/1.0/deals?";
        String storeId = "storeID=" + dealParameters.getStore().getStoreId();
        String upperPrice = "&upperPrice=" + dealParameters.getUpperPrice();
        String sortBy;
        if(!(dealParameters.getSortBy().equals(Sort.DEAL_RATING)))
            sortBy = "&sortBy=" + dealParameters.getSortBy().getSortName();
        else
            sortBy = "";
        String metacriticRating = "";
        String steamRating = "&steamRating=" + dealParameters.getMetacriticRating();
        if (dealParameters.getMetacriticRating() != 0) {
            metacriticRating = "&metacritic=" + dealParameters.getMetacriticRating();
            LOGGER.info("Minimum Metacritic rating set: " + dealParameters.getMetacriticRating());
        }
        if (dealParameters.getSteamRating() != 0) {
            steamRating = "&steamRating=" + dealParameters.getSteamRating();
            LOGGER.info("Minimum Steam rating set: " + dealParameters.getSteamRating());
        }


        LOGGER.info("Selected store:" + dealParameters.getStore().getStoreName() + " (id:" + dealParameters.getStore().getStoreId() + ")");

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet(url + storeId + upperPrice + sortBy + metacriticRating + steamRating);
            LOGGER.info(url + storeId + upperPrice + sortBy + metacriticRating + steamRating);
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
        ArrayList<GameDeal> games = getGameDeals(dealParameters);
        ObservableList<GameDeal> tempList = FXCollections.observableArrayList(games);

        // 2. plug the observable array list into the list
        gameList.setItems(tempList);
    }
}
