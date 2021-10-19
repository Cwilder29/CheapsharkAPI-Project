package screens.list;

import javafx.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.DealParameters;
import model.Deal;
import model.Sort;
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
import screens.ScreenType;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DealListController implements Initializable, SelectedController {
    private static final Logger LOGGER = LogManager.getLogger();

    private final DealParameters dealParameters;

    @FXML
    private ListView<Deal> gameList;

    public DealListController(DealParameters dealParameters) {
        this.dealParameters = dealParameters;
    }

    @FXML
    void clickGame(MouseEvent event) {
        Deal selectedGame;
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

    public ArrayList<Deal> getDeals(DealParameters dealParameters) {
        ArrayList<Deal> deals = new ArrayList<>();
        String url = createGetRequest();

        LOGGER.info("Selected store:" + dealParameters.getStore().getStoreName() + " (id:" + dealParameters.getStore().getStoreId() + ")");

        return fetchDeals(deals, url);
    }

    private String createGetRequest() {
        String url = "https://www.cheapshark.com/api/1.0/deals?";
        url = url + "storeID=" + dealParameters.getStore().getStoreId();
        url = url + "&upperPrice=" + dealParameters.getUpperPrice();
        if(!(dealParameters.getSortBy().equals(Sort.DEAL_RATING)))
            url = url + "&sortBy=" + dealParameters.getSortBy().getSortName();

        if (dealParameters.getMetacriticRating() != 0) {
            url = url + "&metacritic=" + dealParameters.getMetacriticRating();
            LOGGER.info("Minimum Metacritic rating set: " + dealParameters.getMetacriticRating());
        }
        if (dealParameters.getSteamRating() != 0) {
            url = url + "&steamRating=" + dealParameters.getSteamRating();
            LOGGER.info("Minimum Steam rating set: " + dealParameters.getSteamRating());
        }
        if (dealParameters.getOnlyAAA())
            url = url + "&AAA=1";
        if (dealParameters.getOnlySale())
            url = url + "&onSale=1";

        return url;
    }

    private ArrayList<Deal> fetchDeals(ArrayList<Deal> deals, String url) {
        int statusCode;

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet(url);
            LOGGER.info("Connecting to:" + url);
            CloseableHttpResponse response = httpclient.execute(getRequest);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200)
                LOGGER.info("Game deals successfully retrieved from CheapShark: " + statusCode);
            else {
                LOGGER.error("Could not retrieve deals from CheapShark: " + statusCode);
                Alerts.infoAlert("Error!", "Could not retrieve deals from Cheapshark: " + statusCode);
                return deals;
            }

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            JSONArray objResponse = new JSONArray(strResponse);

            for (Object game : objResponse) {
                deals.add(Deal.fromJSONObject((JSONObject) game));
            }

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deals;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. turn plain ol arraylist of models into an ObservableArrayList
        ArrayList<Deal> games = getDeals(dealParameters);
        ObservableList<Deal> tempList = FXCollections.observableArrayList(games);

        // 2. plug the observable array list into the list
        gameList.setItems(tempList);
    }
}
