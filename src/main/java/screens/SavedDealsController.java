package screens;

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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import screens.screentypes.MainMenuScreen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SavedDealsController implements Initializable, SelectedController{
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private ListView<?> gameList;

    @FXML
    void clickGame(MouseEvent event) {
        LOGGER.info("Double Click");
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(new MainMenuScreen().getScreenController());
    }

    @FXML
    void newSearch(ActionEvent event) {
        MainController.getInstance().switchView(new MainMenuScreen().getScreenController());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> deals = null;

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        LOGGER.info("IP Address:- " + inetAddress.getHostAddress());
        String ip = inetAddress.getHostAddress();

        int statusCode;

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet("http://" + ip + ":8080/deals"); // TODO
            LOGGER.info("Connecting to:localhost:8080/deals");
            CloseableHttpResponse response = httpclient.execute(getRequest);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200)
                LOGGER.info("Game deals successfully retrieved from CheapShark: " + statusCode);
            else {
                LOGGER.error("Could not retrieve deals from CheapShark: " + statusCode);
                Alerts.infoAlert("Error!", "Could not retrieve deals from Cheapshark: " + statusCode);
                return;
            }

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            JSONArray objResponse = new JSONArray(strResponse);

            LOGGER.info(objResponse);

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ObservableList<Deal> tempList = FXCollections.observableArrayList(deals);
    }
}
