package screens.save;

import httpclient.DeleteRequest;
import httpclient.GetRequest;
import javafx.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

//    @FXML
//    private ListView<Deal> gameList;

    @FXML
    private TableView<Deal> dealTable;

    @FXML
    private TableColumn<Deal, Float> retailColumn;

    @FXML
    private TableColumn<Deal, Float> saleColumn;

    @FXML
    private TableColumn<Deal, Double> savingsColumn;

    @FXML
    private TableColumn<Deal, String> titleColumn;

    @FXML
    void clickGame(MouseEvent event) {
        Deal selectedDeal;
        if (event.getClickCount() == 2) {
            selectedDeal = dealTable.getSelectionModel().getSelectedItem();
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
        InetAddress inetAddress = null;
        Deal selectedDeal = dealTable.getSelectionModel().getSelectedItem();
        String strResponse;

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
        String url = "http://" + ip + ":8080/deals/" + selectedDeal.getDatabaseId();

        strResponse = new DeleteRequest().executeRequest(url, "");

        if (strResponse != null) {
            dealTable.getItems().remove(selectedDeal);
        }
    }

    private ArrayList<Deal> fetchSavedDeals() {
        ArrayList<Deal> deals = new ArrayList<>();
        InetAddress inetAddress = null;
        String strResponse;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        LOGGER.info("IP Address:- " + inetAddress.getHostAddress());
        String ip = inetAddress.getHostAddress();
        String url = "http://" + ip + ":8080/deals";

        strResponse = new GetRequest().executeRequest(url, "");
        if (strResponse == null)
            return null;
        else {
            JSONArray objResponse = new JSONArray(strResponse);

            for (Object deal : objResponse) {
                deals.add(Deal.fromJSONObjectDatabase((JSONObject) deal));
            }

            LOGGER.info(deals);
            return deals;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Deal> deals = fetchSavedDeals();
        titleColumn.setCellValueFactory(new PropertyValueFactory<Deal, String>("title"));
        savingsColumn.setCellValueFactory(new PropertyValueFactory<Deal, Double>("savings"));
        saleColumn.setCellValueFactory(new PropertyValueFactory<Deal, Float>("salePrice"));
        retailColumn.setCellValueFactory(new PropertyValueFactory<Deal, Float>("normalPrice"));

        if (deals == null) {
            Alerts.infoAlert("Error!", "Could not load saved deals!");
        }
        else {
            ObservableList<Deal> tempList = FXCollections.observableArrayList(deals);
            dealTable.setItems(tempList);
        }
    }
}
