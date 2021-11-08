package javafx.screens.deal;

import javafx.httpclient.GetRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.model.DealParameters;
import javafx.model.Deal;
import javafx.model.Sort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.screens.MainController;
import javafx.screens.SelectedController;
import javafx.screens.screentypes.DealParametersScreen;
import javafx.screens.screentypes.DealViewScreen;
import javafx.screens.screentypes.MainMenuScreen;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DealListController implements Initializable, SelectedController {
    private static final Logger LOGGER = LogManager.getLogger();

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
    private Label pageNumberLabel;

    private final DealParameters dealParameters;
    private ArrayList<Deal> deals;
    private int totalPageNumber;

    public DealListController(DealParameters dealParameters) {
        this.dealParameters = dealParameters;
    }

    @FXML
    void clickGame(MouseEvent event) {
        Deal selectedGame;
        if (event.getClickCount() == 2) {
            selectedGame = dealTable.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                LOGGER.info("Loading information on <" + selectedGame.getTitle() + ">");
                MainController.getInstance().switchView(new DealViewScreen().getScreenController(selectedGame, dealParameters));
            }
        }
    }

    @FXML
    void nextPage(ActionEvent event) {
        LOGGER.info("Loading next page...");
        if (dealParameters.getPageNumber() < totalPageNumber) {
            dealParameters.setPageNumber(dealParameters.getPageNumber() + 1);
            getDeals();
            ObservableList<Deal> tempList = FXCollections.observableArrayList(deals);
            dealTable.setItems(tempList);
        }
        else
            LOGGER.error("Page number cannot be greater than totalPageNumber!");
    }

    @FXML
    void previousPage(ActionEvent event) {
        LOGGER.info("Loading previous page...");
        if (dealParameters.getPageNumber() > 0) {
            dealParameters.setPageNumber(dealParameters.getPageNumber() - 1);
            getDeals();
            ObservableList<Deal> tempList = FXCollections.observableArrayList(deals);
            dealTable.setItems(tempList);
        }
        else
            LOGGER.error("Page number cannot be less than 0!");
    }

    @FXML
    void newSearch(ActionEvent event) {
        MainController.getInstance().switchView(new DealParametersScreen().getScreenController());
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(new MainMenuScreen().getScreenController());
    }

    public void getDeals() {
        deals = new ArrayList<>();
        String url = createGetRequest();
        GetRequest request = new GetRequest();
        String strResponse;

        LOGGER.info("Selected store:" + dealParameters.getStore().getStoreName() + " (id:" + dealParameters.getStore().getId() + ")");
        strResponse = request.executeRequest(url, "");

        if (strResponse != null) {
            JSONArray objResponse = new JSONArray(strResponse);

            for (Object deal : objResponse) {
                deals.add(Deal.fromJSONObject((JSONObject) deal));
            }
            totalPageNumber = request.getTotalPageCount();
            pageNumberLabel.setText((dealParameters.getPageNumber() + 1) + " / " + (totalPageNumber + 1));
        }
    }

    private String createGetRequest() {
        String url = "https://www.cheapshark.com/api/1.0/deals?";
        url = url + "storeID=" + dealParameters.getStore().getId();
        url = url + "&upperPrice=" + dealParameters.getUpperPrice();

        if(!dealParameters.getTitle().isEmpty()) {
            url = url + "&title=" + dealParameters.getTitle();
        }

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
        if (dealParameters.getPageNumber() != 0) {
            url = url + "&pageNumber=" + dealParameters.getPageNumber();
            LOGGER.info("Page set to: " + dealParameters.getPageNumber());
        }
        if (dealParameters.getOnlyAAA())
            url = url + "&AAA=1";
        if (dealParameters.getOnlySale())
            url = url + "&onSale=1";

        return url;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<Deal, String>("title"));
        savingsColumn.setCellValueFactory(new PropertyValueFactory<Deal, Double>("savings"));
        saleColumn.setCellValueFactory(new PropertyValueFactory<Deal, Float>("salePrice"));
        retailColumn.setCellValueFactory(new PropertyValueFactory<Deal, Float>("normalPrice"));

        // 1. turn arraylist of models into an ObservableArrayList
        getDeals();
        ObservableList<Deal> tempList = FXCollections.observableArrayList(deals);

        // 2. plug the observable array list into the table
        dealTable.setItems(tempList);
    }
}
