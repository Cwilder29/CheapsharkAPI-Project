package screens.save;

import javafx.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.Deal;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
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
import screens.screentypes.MainMenuScreen;
import screens.screentypes.SavedDealViewScreen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SavedDealsController implements Initializable, SelectedController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private ListView<Deal> gameList;

    @FXML
    void clickGame(MouseEvent event) {
        Deal selectedDeal;
        if (event.getClickCount() == 2) {
            selectedDeal = gameList.getSelectionModel().getSelectedItem();
            if (selectedDeal != null) {
                LOGGER.info("Loading information on <" + selectedDeal.getTitle() + ">");
                MainController.getInstance().switchView(new SavedDealViewScreen().getScreenController(selectedDeal));
            }
        }
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(new MainMenuScreen().getScreenController());
    }

    @FXML
    void nextPage(ActionEvent event) {
        LOGGER.info("Loading next page...");
    }

    @FXML
    void deleteDeal(ActionEvent event) {

        // TODO Then implement delete from mapping.
        int statusCode;
        InetAddress inetAddress = null;
        Deal selectedDeal = gameList.getSelectionModel().getSelectedItem();
        if (selectedDeal == null) {
            LOGGER.error("No deal selected in menu!");
            return;
        }
        LOGGER.info("Attempting to delete <" + selectedDeal.getTitle() + ">");

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        LOGGER.info("IP Address:- " + inetAddress.getHostAddress());
        String ip = inetAddress.getHostAddress();

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpDelete deleteRequest = new HttpDelete("http://" + ip + ":8080/deals/" + selectedDeal.getDatabaseId()); // TODO change to variable
            LOGGER.info("Connecting to " + ip + ":8080/deals/" + selectedDeal.getDatabaseId());
            CloseableHttpResponse response = httpclient.execute(deleteRequest);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                LOGGER.info("Deal successfully deleted from database: " + statusCode);
                gameList.getItems().remove(selectedDeal);
            }
            else {
                LOGGER.error("Could not retrieve delete deal from database: " + statusCode);
                Alerts.infoAlert("Error!", "Could not retrieve delete deal from database: " + statusCode);
                return;
            }

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Deal> fetchSavedDeals() {
        ArrayList<Deal> deals = new ArrayList<>();
        int statusCode;
        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        LOGGER.info("IP Address:- " + inetAddress.getHostAddress());
        String ip = inetAddress.getHostAddress();

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet("http://" + ip + ":8080/deals"); // TODO change to variable
            LOGGER.info("Connecting to " + ip + ":8080/deals");
            CloseableHttpResponse response = httpclient.execute(getRequest);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200)
                LOGGER.info("Game deals successfully retrieved from Database: " + statusCode);
            else {
                LOGGER.error("Could not retrieve deals from CheapShark: " + statusCode);
                Alerts.infoAlert("Error!", "Could not retrieve deals from Database: " + statusCode);
                return null;
            }

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            JSONArray objResponse = new JSONArray(strResponse);

            for (Object game : objResponse) {
                deals.add(Deal.fromJSONObjectDatabase((JSONObject) game));
            }

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info(deals);
        return deals;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Deal> deals = fetchSavedDeals();

        if (deals == null) {
            Alerts.infoAlert("Error!", "Could not load saved deals!");
        }
        else {
            ObservableList<Deal> tempList = FXCollections.observableArrayList(deals);
            gameList.setItems(tempList);
        }
    }
}
